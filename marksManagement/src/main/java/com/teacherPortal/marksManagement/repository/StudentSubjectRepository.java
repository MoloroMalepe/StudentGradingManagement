package com.teacherPortal.marksManagement.repository;


import com.teacherPortal.marksManagement.model.StudentSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentSubjectRepository extends JpaRepository<StudentSubject, Long> {

    // Find by student and subject (unique combination)
    Optional<StudentSubject> findByStudentIdAndSubjectId(Long studentId, Long subjectId);

    // Find all assignments for a student
    List<StudentSubject> findByStudentId(Long studentId);

    // Find all assignments for a subject
    List<StudentSubject> findBySubjectId(Long subjectId);

    // Find all assignments for a teacher
    List<StudentSubject> findByTeacherId(Long teacherId);

    // Find active assignments
    List<StudentSubject> findByIsActiveTrue();

    // Check if assignment already exists
    Boolean existsByStudentIdAndSubjectId(Long studentId, Long subjectId);
}