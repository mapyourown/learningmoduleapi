package com.mapyourown.Learning.controllers;

import com.mapyourown.Learning.models.Course;
import com.mapyourown.Learning.models.CourseModule;
import com.mapyourown.Learning.models.Lesson;
import com.mapyourown.Learning.payload.request.CourseRequest;
import com.mapyourown.Learning.payload.request.ModuleRequest;
import com.mapyourown.Learning.repository.CourseRepository;
import com.mapyourown.Learning.repository.ModuleRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value="/api", produces = "application/json")
<<<<<<< Updated upstream
@CrossOrigin(origins = "http://159.223.117.248:8082")
=======
@CrossOrigin(origins = {"https://localhost:3000", "http://localhost:3000", "http://lms.mapyourown.com:8082", "https://lms.mapyourown.com", "http://159.223.117.248:8082", "https://159.223.117.248"})

>>>>>>> Stashed changes
public class ModuleController {
    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    CourseRepository courseRepository;

    @PostMapping("/courseModules")
    public ResponseEntity<?> createCourseModule(@Valid @RequestBody ModuleRequest moduleRequest) {
        try {
            //fetch course
            Course course = courseRepository.getReferenceById(moduleRequest.getCourseId());
            CourseModule courseModule = new CourseModule(moduleRequest.getName(), course);
            CourseModule _newModule = moduleRepository.save(courseModule);
            return new ResponseEntity<>(_newModule, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/courseModules/{id}")
    public ResponseEntity<?> updateCourseModule(@PathVariable("id") long id, @RequestBody CourseModule courseModuleRequest){
        Optional<CourseModule> moduleData = moduleRepository.findById(id);
        if (moduleData.isPresent()) {
            CourseModule _newCourseModule = moduleData.get();
            _newCourseModule.setName(courseModuleRequest.getName());

            return new ResponseEntity<>(moduleRepository.save(_newCourseModule), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/courseModules/{id}/lesson/{lessonId}")
    public ResponseEntity<?> getLessonByModule(@PathVariable("id") long id, @PathVariable("lessonId") int lessonId){
        Optional<CourseModule> moduleData = moduleRepository.findById(id);
        if (moduleData.isPresent()) {
            Lesson _getLesson = moduleData.get().getLessons().get(lessonId-1);

            return new ResponseEntity<>(_getLesson, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/courseModules")
    public ResponseEntity<List<CourseModule>> getAllCourseModule(){
        List<CourseModule> courseModules = new ArrayList<CourseModule>();
        moduleRepository.findAll().forEach(courseModules::add);
        if (courseModules.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(courseModules, HttpStatus.OK);
    }

    @GetMapping("/courseModules/{id}")
    public ResponseEntity<CourseModule> getCourseById(@PathVariable("id") long id) {
        Optional<CourseModule> modulesData = moduleRepository.findById(id);

        if (modulesData.isPresent()) {
            return new ResponseEntity<>(modulesData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/courseModule")
    public ResponseEntity<HttpStatus> deleteCourseModule(@PathVariable("id") Long id ){
        try {
            moduleRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
