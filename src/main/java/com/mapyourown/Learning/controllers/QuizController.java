package com.mapyourown.Learning.controllers;

import com.mapyourown.Learning.models.*;
import com.mapyourown.Learning.payload.request.ModuleRequest;
import com.mapyourown.Learning.payload.request.QuizRequest;
import com.mapyourown.Learning.payload.request.StudentQuizAttemptRequest;
import com.mapyourown.Learning.repository.CourseRepository;
import com.mapyourown.Learning.repository.QuizRepository;
import com.mapyourown.Learning.repository.StudentQuizAttemptRepository;
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
public class QuizController {
    @Autowired
    QuizRepository quizRepository;

    @Autowired
    CourseRepository courseRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    StudentQuizAttemptRepository studentQuizAttemptRepository;

    @PostMapping("/quiz")
    public ResponseEntity<?> createQuiz(@Valid @RequestBody QuizRequest quizRequest) {
        try {
            //fetch course
            Course course = courseRepository.getReferenceById( quizRequest.getCourseId());

            Quiz quiz = new Quiz(quizRequest.getName(), quizRequest.getNumber(), quizRequest.getCourseOrder(), quizRequest.getMinPassScore(), quizRequest.isPassRequired(),course);
            Quiz _newQuiz= quizRepository.save(quiz);
            return new ResponseEntity<>(_newQuiz, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/quizzes")
    public ResponseEntity<List<Quiz>> getAllQuiz(){
        List<Quiz> quizzes = new ArrayList<>();
        quizRepository.findAll().forEach(quizzes::add);
        if (quizzes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(quizzes, HttpStatus.OK);
    }

    @DeleteMapping("/quiz/{id}")
    public ResponseEntity<HttpStatus> deleteCourseModule(@PathVariable("id") Long id ){
        try {
            quizRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/studentQuizAttempt")
    public ResponseEntity<?> createStudentQuizAttempt(@Valid @RequestBody StudentQuizAttemptRequest studentQuizAttemptRequest){
        try {
            //fetch
            Quiz quiz = quizRepository.getReferenceById(studentQuizAttemptRequest.getQuizId());

            //fetch user
            Optional<User> userOptional = userRepository.findById(studentQuizAttemptRequest.getUserId());
            User user  = userOptional.get();
            StudentQuizAttempt studentQuizAttempt = new StudentQuizAttempt(studentQuizAttemptRequest.getAttemptDatetime(),studentQuizAttemptRequest.getScoreAchieved(), quiz, user);
            StudentQuizAttempt _studentQuizAttempt = studentQuizAttemptRepository.save(studentQuizAttempt);
            return new ResponseEntity<>(_studentQuizAttempt, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/studentQuizAttempts")
    public ResponseEntity<List<StudentQuizAttempt>> getAllStudentQuizAttempts(){
        try {
            List<StudentQuizAttempt> studentQuizAttempts = new ArrayList<StudentQuizAttempt>();

            studentQuizAttemptRepository.findAll().forEach(studentQuizAttempts::add);

            if (studentQuizAttempts.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(studentQuizAttempts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
