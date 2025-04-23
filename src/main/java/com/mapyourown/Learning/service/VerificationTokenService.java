package com.mapyourown.Learning.service;

import com.mapyourown.Learning.models.User;
import com.mapyourown.Learning.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationTokenService {
    @Autowired
    private UserRepository userRepository;

    public void createVerificationToken(User user, String token) {
        user.setVerificationToken(token);
        userRepository.save(user);
    }

    public String validateVerificationToken(String token) {
        User user = userRepository.findByVerificationToken(token).orElse(null);
        if (user == null) {
            return "invalid";
        }

        user.setEnabled(true);
        userRepository.save(user);
        return "valid";
    }


}
