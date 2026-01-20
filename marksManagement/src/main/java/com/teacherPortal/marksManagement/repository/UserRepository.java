package com.teacherPortal.marksManagement.repository;

import com.teacherPortal.marksManagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by username
    Optional<User> findByUsername(String username);

    // Check if username exists
    Boolean existsByUsername(String username);

    // Check if email exists - ADD THIS METHOD
    Boolean existsByEmail(String email);

    // Find all teachers created by specific admin
    List<User> findByCreatedBy(String createdBy);

    // Find all users by role
    List<User> findByRole(String role);
}