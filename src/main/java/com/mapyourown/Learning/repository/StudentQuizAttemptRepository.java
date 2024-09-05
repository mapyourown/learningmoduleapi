package com.mapyourown.Learning.repository;

import com.mapyourown.Learning.models.StudentQuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentQuizAttemptRepository extends JpaRepository<StudentQuizAttempt, Long> {
}
