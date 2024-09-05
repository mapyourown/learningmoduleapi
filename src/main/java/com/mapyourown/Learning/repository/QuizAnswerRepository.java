package com.mapyourown.Learning.repository;

import com.mapyourown.Learning.models.QuizAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizAnswerRepository extends JpaRepository<QuizAnswer, Long> {
}
