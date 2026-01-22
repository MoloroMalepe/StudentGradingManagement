package com.teacherPortal.marksManagement.repository;

import com.teacherPortal.marksManagement.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {


    Optional<Student> findByStudentNumber(String studentNumber);

    Boolean existsByStudentNumber(String studentNumber);

    List<Student> findByIsActiveTrue();

    List<Student> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);
}
