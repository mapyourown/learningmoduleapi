package com.mapyourown.Learning.repository;
import java.util.Optional;

import com.mapyourown.Learning.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends JpaRepository<User, Long>  {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);
    Optional<User> findByVerificationToken(String token);

}
