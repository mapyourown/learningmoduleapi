package com.mapyourown.Learning.controllers;

import java.util.*;
import java.util.stream.Collectors;

import com.mapyourown.Learning.payload.request.PaymentRequest;
import com.mapyourown.Learning.service.EmailService;
import com.mapyourown.Learning.service.VerificationTokenService;
import com.stripe.Stripe;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import com.mapyourown.Learning.models.ERole;
import com.mapyourown.Learning.models.Role;
import com.mapyourown.Learning.models.User;
import com.mapyourown.Learning.payload.request.LoginRequest;
import com.mapyourown.Learning.payload.request.SignupRequest;
import com.mapyourown.Learning.payload.response.JwtResponse;
import com.mapyourown.Learning.payload.response.MessageResponse;
import com.mapyourown.Learning.repository.RoleRepository;
import com.mapyourown.Learning.repository.UserRepository;
import com.mapyourown.Learning.security.jwt.JwtUtils;
import com.mapyourown.Learning.security.services.UserDetailsImpl;
import java.util.UUID;

@CrossOrigin(origins = {"https://localhost:3000", "http://localhost:3000", "http://lms.mapyourown.com:8082", "http://lms.mapyourown.com", "http://159.223.117.248:8082", "http://159.223.117.248"})

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    VerificationTokenService verificationTokenService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    private EmailService emailService;

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));


        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

            String[] parts = jwt.split("\\.");

            JSONObject header = new JSONObject(decode(parts[0]));
            JSONObject payload = new JSONObject(decode(parts[1]));
            String signature = decode(parts[2]);
            long exp = payload.getLong("exp");
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.isEnabled(),
                exp,
                roles));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()), signUpRequest.getFirstName(), signUpRequest.getLastName(), signUpRequest.getAddress(), signUpRequest.getCity(), signUpRequest.getState());
        user.setEnabled(false);
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        // Generate verification token and send email
        String token = UUID.randomUUID().toString();
        user.setVerificationToken(token);
        userRepository.save(user);

        String confirmationUrl = "http://localhost:3000/verify-email/" + token;
        emailService.sendEmail(user.getEmail(), "Email Verification", "Click the link to verify your email: " + confirmationUrl);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @GetMapping("/verify-email")
    //@RequestParam in case I need to use instead of @PathVariable
    public ResponseEntity<?> verifyEmail(@PathVariable("token") String token) {
        String result = verificationTokenService.validateVerificationToken(token);
        if (result.equals("valid")) {
            return ResponseEntity.ok(new MessageResponse("Your account has been verified successfully."));

        } else {
            return ResponseEntity.ok(new MessageResponse("Invalid verification token."));
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return ResponseEntity.ok(new MessageResponse("User logout successfully!"));
    }

    private static String decode(String encodedString) {
        return new String(Base64.getUrlDecoder().decode(encodedString));
    }

    @PostMapping("/paymentIntent")
    public Map<String, String> createPaymentIntent(@RequestBody PaymentRequest request) throws StripeException {
        Stripe.apiKey = stripeSecretKey;

        if (request.getAmount() <= 0) {
            throw new IllegalArgumentException("Invalid amount: Must be greater than 0.");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("amount", request.getAmount()); // Amount from frontend (already in cents)
        params.put("currency", "usd");
        params.put("payment_method_types", new String[]{"card"});
        // Store customer details in metadata
        Map<String, String> metadata = new HashMap<>();
        metadata.put("name", request.getName());
        metadata.put("email", request.getEmail());
        metadata.put("phone", request.getPhone());
        params.put("metadata", metadata);
        Map<String, String> response = new HashMap<>();
        try {
            PaymentIntent intent = PaymentIntent.create(params);
            response.put("clientSecret", intent.getClientSecret());
        }  catch (CardException e) {
           System.out.println("A payment error occurred: {}");
        } catch (InvalidRequestException e) {
            System.out.println("An invalid request occurred.");
        } catch (Exception e) {
            System.out.println("Another problem occurred, maybe unrelated to Stripe.");
        }
        return response;
    }
}
