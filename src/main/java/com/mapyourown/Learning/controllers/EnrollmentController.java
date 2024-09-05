package com.mapyourown.Learning.controllers;

import com.mapyourown.Learning.models.Course;
import com.mapyourown.Learning.models.Enrollment;
import com.mapyourown.Learning.models.User;
import com.mapyourown.Learning.payload.request.CourseRequest;
import com.mapyourown.Learning.payload.request.EnrollmentRequest;
import com.mapyourown.Learning.repository.CourseRepository;
import com.mapyourown.Learning.repository.EnrollmentRepository;
import com.mapyourown.Learning.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EnrollmentController {
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    EnrollmentRepository enrollmentRepository;

    @PostMapping("/enrollment")
    public ResponseEntity<?> createEnrollment(@Valid @RequestBody EnrollmentRequest enrollmentRequest){

       try {
           //fetch course
           Course course = courseRepository.getReferenceById(enrollmentRequest.getCourseId());

           //fetch user
           Optional<User> userOptional = userRepository.findById(enrollmentRequest.getUserId());
           User user  = userOptional.get();
           Enrollment enrollment = new Enrollment(enrollmentRequest.getEnrollmentDate(), enrollmentRequest.getCompletedDate(), user, course);
           Enrollment _enrollment = enrollmentRepository.save(enrollment);
           return new ResponseEntity<>(_enrollment, HttpStatus.CREATED);
       } catch (Exception e){
           return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
       }

    }

    @GetMapping("/enrollments")
    public ResponseEntity<List<Enrollment>> getAllEnrollment(@RequestParam(required = false) String name) {
        try {
            List<Enrollment> enrollments = new ArrayList<Enrollment>();

            enrollmentRepository.findAll().forEach(enrollments::add);

            if (enrollments.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(enrollments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/enrollment/{id}")
    public ResponseEntity<HttpStatus> deleteEnrollment(@PathVariable("id") long id) {
        try {
            enrollmentRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/enrollment/{id}")
    public ResponseEntity<Enrollment> getEnrollmentById(@PathVariable("id") long id) {
        Optional<Enrollment> enrollmentData = enrollmentRepository.findById(id);

        if (enrollmentData.isPresent()) {
            return new ResponseEntity<>(enrollmentData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



}
