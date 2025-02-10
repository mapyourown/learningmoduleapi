package com.mapyourown.Learning.controllers;

import com.mapyourown.Learning.models.*;
import com.mapyourown.Learning.payload.request.LessonRequest;
import com.mapyourown.Learning.payload.request.StudentLessonRequest;
import com.mapyourown.Learning.repository.LessonRepository;
import com.mapyourown.Learning.repository.ModuleRepository;
import com.mapyourown.Learning.repository.StudentLessonRepository;
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
@CrossOrigin(origins = "http://159.223.117.248:8082")
public class LessonController {
    @Autowired
    ModuleRepository moduleRepository;
    @Autowired
    LessonRepository lessonRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    StudentLessonRepository studentLessonRepository;

    @PostMapping("/lessons")
    public ResponseEntity<?> createLesson(@Valid @RequestBody LessonRequest lessonRequest){
        try {
            //fetch module
            CourseModule courseModule = moduleRepository.getReferenceById(lessonRequest.getModuleId());

            //fetch user
            Lesson lesson = new Lesson(lessonRequest.getName(), lessonRequest.getNumber(), lessonRequest.getVideoUrl(), lessonRequest.getLessonDetails(), lessonRequest.getCourseOrders(), courseModule);
            Lesson _lesson = lessonRepository.save(lesson);
            return new ResponseEntity<>(_lesson, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/lessons")
    public ResponseEntity<List<Lesson>> getAllLessons(){
        try {
            List<Lesson> lessons = new ArrayList<Lesson>();

            lessonRepository.findAll().forEach(lessons::add);

            if (lessons.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(lessons, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/lessons/{id}")
    public ResponseEntity<Lesson> getCourseById(@PathVariable("id") long id) {
        Optional<Lesson> lessonData = lessonRepository.findById(id);

        if (lessonData.isPresent()) {
            return new ResponseEntity<>(lessonData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/lessons/{id}")
    public ResponseEntity<?> updateLesson(@PathVariable("id") long id, @RequestBody Lesson lessonRequest){
        Optional<Lesson> lessonData = lessonRepository.findById(id);
        if (lessonData.isPresent()) {
            Lesson _newLesson = lessonData.get();
            _newLesson.setName(lessonRequest.getName());
            _newLesson.setNumber(lessonRequest.getNumber());
            _newLesson.setVideoUrl(lessonRequest.getVideoUrl());
            _newLesson.setLessonDetails(lessonRequest.getLessonDetails());
            _newLesson.setCourseOrders(lessonRequest.getCourseOrders());
            return new ResponseEntity<>(lessonRepository.save(_newLesson), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/lessons/{id}")
    public ResponseEntity<HttpStatus> deleteModule(@PathVariable("id") Long id ){
        try {
            lessonRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/studentLessons")
    public ResponseEntity<?> createLessonEnrollment(@Valid @RequestBody StudentLessonRequest studentLessonRequest){
        try {
            //fetch lesson
            Lesson lesson = lessonRepository.getReferenceById(studentLessonRequest.getLessonId());

            //fetch user
            Optional<User> userOptional = userRepository.findById(studentLessonRequest.getUserId());
            User user  = userOptional.get();
            StudentLesson studentLesson = new StudentLesson( lesson, user);
            StudentLesson _studentLesson = studentLessonRepository.save(studentLesson);
            return new ResponseEntity<>(_studentLesson, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/studentLessons")
    public ResponseEntity<List<StudentLesson>> getAllStudentLessons(){
        try {
            List<StudentLesson> studentLessons = new ArrayList<StudentLesson>();

            studentLessonRepository.findAll().forEach(studentLessons::add);

            if (studentLessons.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(studentLessons, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
