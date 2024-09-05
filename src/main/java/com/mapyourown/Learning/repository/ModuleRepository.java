package com.mapyourown.Learning.repository;

import com.mapyourown.Learning.models.CourseModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRepository extends JpaRepository<CourseModule, Long> {
}
