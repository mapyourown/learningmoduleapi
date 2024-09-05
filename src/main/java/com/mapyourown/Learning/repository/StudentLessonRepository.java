package com.mapyourown.Learning.repository;

import com.mapyourown.Learning.models.StudentLesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentLessonRepository extends JpaRepository<StudentLesson, Long> {
}
