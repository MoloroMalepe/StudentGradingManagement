package com.teacherPortal.marksManagement.repository;


import com.teacherPortal.marksManagement.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    // Find by subject code (unique)
    Optional<Subject> findBySubjectCode(String subjectCode);

    // Check if subject code exists
    Boolean existsBySubjectCode(String subjectCode);

    // Find active subjects
    List<Subject> findByIsActiveTrue();

    // Search by subject code or name
    List<Subject> findBySubjectCodeContainingIgnoreCaseOrSubjectNameContainingIgnoreCase(
            String subjectCode, String subjectName);
}
