package com.mapyourown.Learning.repository;

import com.mapyourown.Learning.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
        List<Course> findByIsPublished(boolean isPublished);
        List<Course> findByNameContaining(String name);
}

