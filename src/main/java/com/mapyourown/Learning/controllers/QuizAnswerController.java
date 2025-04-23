package com.mapyourown.Learning.controllers;

import com.mapyourown.Learning.models.Quiz;
import com.mapyourown.Learning.models.QuizAnswer;
import com.mapyourown.Learning.models.QuizQuestion;
import com.mapyourown.Learning.payload.request.QuizAnswerRequest;
import com.mapyourown.Learning.payload.request.QuizQuestionRequest;
import com.mapyourown.Learning.payload.request.QuizRequest;
import com.mapyourown.Learning.repository.QuizAnswerRepository;
import com.mapyourown.Learning.repository.QuizQuestionRepository;
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
@CrossOrigin(origins = {"https://localhost:3000", "http://localhost:3000", "http://lms.mapyourown.com:8082", "http://lms.mapyourown.com", "http://159.223.117.248:8082", "http://159.223.117.248"})
public class QuizAnswerController {

    @Autowired
    QuizAnswerRepository quizAnswerRepository;

    @Autowired
    QuizQuestionRepository quizQuestionRepository;


    @PostMapping("/quizAnswer")
    public ResponseEntity<?> createQuiz(@Valid @RequestBody QuizAnswerRequest quizAnswerRequest) {
        try {
            //fetch quiz
            QuizQuestion quizQuestion = quizQuestionRepository.getReferenceById( quizAnswerRequest.getQuizQuestionId());

            QuizAnswer quizAnswer = new QuizAnswer(quizAnswerRequest.getAnswerText(), quizAnswerRequest.getIsCorrect(), quizQuestion);
            QuizAnswer _newQuizAnswer= quizAnswerRepository.save(quizAnswer);
            return new ResponseEntity<>(_newQuizAnswer, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/quizAnswers")
    public ResponseEntity<List<QuizAnswer>> getAllQuizQuestions(){
        List<QuizAnswer> quizAnswers = new ArrayList<>();
        quizAnswerRepository.findAll().forEach(quizAnswers::add);
        if (quizAnswers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(quizAnswers, HttpStatus.OK);
    }

    @DeleteMapping("/quizAnswer/{id}")
    public ResponseEntity<HttpStatus> deleteCourseModule(@PathVariable("id") Long id ){
        try {
            quizAnswerRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/quizAnswer/{id}")
    public ResponseEntity<QuizAnswer> updateQuizAnswer(@PathVariable("id") long id, @RequestBody QuizAnswerRequest quizAnswerRequest) {
        Optional<QuizAnswer> quizAnswerData = quizAnswerRepository.findById(id);
        QuizQuestion quizQuestion = quizQuestionRepository.getReferenceById( quizAnswerRequest.getQuizQuestionId());

        if (quizAnswerData.isPresent()) {
            QuizAnswer _newQuizAnswer = quizAnswerData.get();
            _newQuizAnswer.setAnswerText(quizAnswerRequest.getAnswerText());
            _newQuizAnswer.setIsCorrect(quizAnswerRequest.getIsCorrect());
            _newQuizAnswer.setQuizQuestion(quizQuestion);
            return new ResponseEntity<>(quizAnswerRepository.save(_newQuizAnswer), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
