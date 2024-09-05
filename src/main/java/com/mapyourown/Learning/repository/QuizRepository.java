package com.mapyourown.Learning.repository;

import com.mapyourown.Learning.models.CourseModule;
import com.mapyourown.Learning.models.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
}
