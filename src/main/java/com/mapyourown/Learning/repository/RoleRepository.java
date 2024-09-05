package com.mapyourown.Learning.repository;

import java.util.Optional;

import com.mapyourown.Learning.models.ERole;
import com.mapyourown.Learning.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>  {
    Optional<Role> findByName(ERole name);
}
