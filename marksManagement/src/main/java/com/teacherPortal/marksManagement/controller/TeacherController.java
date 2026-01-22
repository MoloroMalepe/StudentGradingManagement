package com.teacherPortal.marksManagement.controller;

import com.teacherPortal.marksManagement.model.dto.AssignmentResponseDTO;
import com.teacherPortal.marksManagement.service.StudentSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher")
@PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")  // Teachers and Admin can access
public class TeacherController {

    @Autowired
    private StudentSubjectService studentSubjectService;

    // Get teacher's own assignments (based on logged-in teacher)
    @GetMapping("/assignments")
    public ResponseEntity<List<AssignmentResponseDTO>> getMyAssignments(Authentication authentication) {
        try {
            // Get teacher ID from authentication (teacher's username)
            String username = authentication.getName();

            // For now, we'll need to get teacher ID from username
            // This is simplified - in real app, you'd get user ID from authentication
            // For testing, we'll use a mock endpoint
            return ResponseEntity.ok(studentSubjectService.getAssignmentsByTeacher(1L)); // Mock teacher ID
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Get students assigned to teacher for a specific subject
    @GetMapping("/assignments/subject/{subjectId}")
    public ResponseEntity<List<AssignmentResponseDTO>> getMySubjectAssignments(
            @PathVariable Long subjectId, Authentication authentication) {
        try {
            // This would filter by both teacher and subject
            // For now, return all assignments for subject
            return ResponseEntity.ok(studentSubjectService.getAssignmentsBySubject(subjectId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}