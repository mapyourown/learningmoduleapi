package com.mapyourown.Learning.repository;

import com.mapyourown.Learning.models.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
}
