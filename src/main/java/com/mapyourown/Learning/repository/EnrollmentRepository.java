package com.mapyourown.Learning.repository;

import com.mapyourown.Learning.models.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

}
