package com.mapyourown.Learning.controllers;

import com.mapyourown.Learning.models.*;
import com.mapyourown.Learning.payload.request.CourseRequest;
import com.mapyourown.Learning.payload.request.ModuleRequest;
import com.mapyourown.Learning.payload.request.QuizRequest;
import com.mapyourown.Learning.payload.request.StudentQuizAttemptRequest;
import com.mapyourown.Learning.repository.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"https://localhost:3000", "http://localhost:3000", "http://lms.mapyourown.com:8082", "http://lms.mapyourown.com", "http://159.223.117.248:8082", "http://159.223.117.248"})
public class QuizController {
    @Autowired
    QuizRepository quizRepository;

    @Autowired
    ModuleRepository moduleRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    StudentQuizAttemptRepository studentQuizAttemptRepository;

    @PostMapping("/quiz")
    public ResponseEntity<?> createQuiz(@Valid @RequestBody QuizRequest quizRequest) {
        try {
            //fetch course
            CourseModule courseModule = moduleRepository.getReferenceById( quizRequest.getCourseModuleId());

            Quiz quiz = new Quiz(quizRequest.getName(), quizRequest.getNumber(), quizRequest.getCourseOrder(), quizRequest.getMinPassScore(), quizRequest.isPassRequired(),courseModule);
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

    @GetMapping("/quiz/{id}")
    public ResponseEntity<Quiz> getSingleQuiz(@PathVariable("id") Long id){
        Optional<Quiz> quizData = quizRepository.findById(id);

        if (quizData.isPresent()) {
            return new ResponseEntity<>(quizData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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

    @PutMapping("/quiz/{id}")
    public ResponseEntity<Quiz> updateQuiz(@PathVariable("id") long id, @RequestBody QuizRequest quizRequest) {
        Optional<Quiz> quizData = quizRepository.findById(id);

        if (quizData.isPresent()) {
            Quiz _newQuiz = quizData.get();
            _newQuiz.setName(quizRequest.getName());
            _newQuiz.setNumber(quizRequest.getNumber());
            _newQuiz.setCourseOrder(quizRequest.getCourseOrder());
            _newQuiz.setMinPassScore(quizRequest.getMinPassScore());
            _newQuiz.setPassRequired(quizRequest.isPassRequired());
            return new ResponseEntity<>(quizRepository.save(_newQuiz), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
