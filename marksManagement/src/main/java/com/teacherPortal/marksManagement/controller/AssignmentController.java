package com.teacherPortal.marksManagement.controller;

import com.teacherPortal.marksManagement.model.dto.AssignmentDTO;
import com.teacherPortal.marksManagement.model.dto.AssignmentResponseDTO;
import com.teacherPortal.marksManagement.service.StudentSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/assignments")
@PreAuthorize("hasRole('ADMIN')")
public class AssignmentController {

    @Autowired
    private StudentSubjectService studentSubjectService;

    // Create new assignment
    @PostMapping("/")
    public ResponseEntity<?> createAssignment(@RequestBody AssignmentDTO assignmentDTO) {
        try {
            AssignmentResponseDTO createdAssignment = studentSubjectService.createAssignment(assignmentDTO);
            return ResponseEntity.ok(createdAssignment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating assignment: " + e.getMessage());
        }
    }

    // Get all assignments
    @GetMapping("/")
    public ResponseEntity<List<AssignmentResponseDTO>> getAllAssignments() {
        List<AssignmentResponseDTO> assignments = studentSubjectService.getAllAssignments();
        return ResponseEntity.ok(assignments);
    }

    // Get assignments by student
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<AssignmentResponseDTO>> getAssignmentsByStudent(@PathVariable Long studentId) {
        List<AssignmentResponseDTO> assignments = studentSubjectService.getAssignmentsByStudent(studentId);
        return ResponseEntity.ok(assignments);
    }

    // Get assignments by teacher
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<AssignmentResponseDTO>> getAssignmentsByTeacher(@PathVariable Long teacherId) {
        List<AssignmentResponseDTO> assignments = studentSubjectService.getAssignmentsByTeacher(teacherId);
        return ResponseEntity.ok(assignments);
    }

    // Get assignments by subject
    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<List<AssignmentResponseDTO>> getAssignmentsBySubject(@PathVariable Long subjectId) {
        List<AssignmentResponseDTO> assignments = studentSubjectService.getAssignmentsBySubject(subjectId);
        return ResponseEntity.ok(assignments);
    }

    // Deactivate assignment
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deactivateAssignment(@PathVariable Long id) {
        try {
            studentSubjectService.deactivateAssignment(id);
            return ResponseEntity.ok("Assignment deactivated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deactivating assignment: " + e.getMessage());
        }
    }
}