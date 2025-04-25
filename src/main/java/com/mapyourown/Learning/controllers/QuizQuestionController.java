package com.mapyourown.Learning.controllers;

import com.mapyourown.Learning.models.*;
import com.mapyourown.Learning.payload.request.QuizQuestionRequest;
import com.mapyourown.Learning.payload.request.QuizRequest;
import com.mapyourown.Learning.payload.request.StudentLessonRequest;
import com.mapyourown.Learning.payload.request.StudentQuizAttemptRequest;
import com.mapyourown.Learning.repository.QuizQuestionRepository;
import com.mapyourown.Learning.repository.QuizRepository;
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
<<<<<<< Updated upstream
=======
@CrossOrigin(origins = {"https://localhost:3000", "http://localhost:3000", "http://lms.mapyourown.com:8082", "https://lms.mapyourown.com", "http://159.223.117.248:8082", "https://159.223.117.248"})
>>>>>>> Stashed changes
public class QuizQuestionController {
    @Autowired
    QuizQuestionRepository quizQuestionRepository;
    @Autowired
    QuizRepository quizRepository;

    @PostMapping("/quizQuestion")
    public ResponseEntity<?> createQuiz(@Valid @RequestBody QuizQuestionRequest quizQuestionRequest) {
        try {
            //fetch quiz
            Quiz quiz = quizRepository.getReferenceById(quizQuestionRequest.getQuizId());

            QuizQuestion quizQuestion = new QuizQuestion(quizQuestionRequest.getQuestionTitle(), quizQuestionRequest.getQuestionType(), quiz);
            QuizQuestion _newQuizQuestion= quizQuestionRepository.save(quizQuestion);
            return new ResponseEntity<>(_newQuizQuestion, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/quizQuestions")
    public ResponseEntity<List<QuizQuestion>> getAllQuizQuestions(){
        List<QuizQuestion> quizQuestions = new ArrayList<>();
        quizQuestionRepository.findAll().forEach(quizQuestions::add);
        if (quizQuestions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(quizQuestions, HttpStatus.OK);
    }

    @DeleteMapping("/quizQuestion/{id}")
    public ResponseEntity<HttpStatus> deleteCourseModule(@PathVariable("id") Long id ){
        try {
            quizQuestionRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
