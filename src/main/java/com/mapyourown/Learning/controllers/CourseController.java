package com.mapyourown.Learning.controllers;

import com.mapyourown.Learning.models.Course;
import com.mapyourown.Learning.models.Enrollment;
import com.mapyourown.Learning.models.User;
import com.mapyourown.Learning.payload.request.CourseRequest;
import com.mapyourown.Learning.payload.request.LoginRequest;
import com.mapyourown.Learning.repository.CourseRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://159.223.117.248:8082")
@RequestMapping("/api")
public class CourseController {
    @Autowired
    CourseRepository courseRepository;

    @PostMapping("/courses")
    public ResponseEntity<?> createCourse(@Valid @RequestBody CourseRequest courseRequest) {
        try {
            // Create new course
            Course course = new Course(courseRequest.getName(),
                    courseRequest.getDescription(),
                    courseRequest.isProgress(), courseRequest.getPrice(), courseRequest.isPublished());
            //List<Enrollment> enrollments = new ArrayList<>();
            //for (Enrollment enrollmentIn : course.getEnrollments()) {
                // new enrollment
                //Enrollment enrollment = new Enrollment(enrollmentIn.getEnrollmentDate(), enrollmentIn.getCompletedDate(), enrollmentIn.getUser());
                // set owner to Blog
                //enrollment.setCourse(course);
                // add blog to list
                //enrollments.add(enrollment);
            //}

            // add enrollment list to course
            //course.setEnrollments(enrollments);

            Course _newCourse = courseRepository.save(course);
            return new ResponseEntity<>(_newCourse, HttpStatus.CREATED);

        } catch(Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getAllCourses(@RequestParam(required = false) String name) {
        try {
            List<Course> courses = new ArrayList<Course>();

            if (name == null)
                courseRepository.findAll().forEach(courses::add);
            else
                courseRepository.findByNameContaining(name).forEach(courses::add);

            if (courses.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(courses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/courses/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable("id") long id) {
        Optional<Course> courseData = courseRepository.findById(id);

        if (courseData.isPresent()) {
            return new ResponseEntity<>(courseData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/courses/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable("id") long id, @RequestBody CourseRequest courseRequest) {
        Optional<Course> courseData = courseRepository.findById(id);

        if (courseData.isPresent()) {
            Course _newCourse = courseData.get();
            _newCourse.setName(courseRequest.getName());
            _newCourse.setDescription(courseRequest.getDescription());
            _newCourse.setPublished(courseRequest.isPublished());
            _newCourse.setProgress(courseRequest.isProgress());
            _newCourse.setPrice(courseRequest.getPrice());
            return new ResponseEntity<>(courseRepository.save(_newCourse), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/courses/{id}")
    public ResponseEntity<HttpStatus> deleteCourses(@PathVariable("id") long id) {
        try {
            courseRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
