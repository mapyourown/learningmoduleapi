package com.mapyourown.Learning.repository;

import com.mapyourown.Learning.models.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    Optional<Enrollment> findByUserId(Long userId);


}
