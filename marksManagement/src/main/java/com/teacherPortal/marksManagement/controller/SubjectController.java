package com.teacherPortal.marksManagement.controller;

import com.teacherPortal.marksManagement.model.dto.SubjectDTO;
import com.teacherPortal.marksManagement.model.dto.SubjectResponseDTO;
import com.teacherPortal.marksManagement.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/subjects")
@PreAuthorize("hasRole('ADMIN')")  // Only ADMIN can access
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    // Create new subject
    @PostMapping("/")
    public ResponseEntity<?> createSubject(@RequestBody SubjectDTO subjectDTO) {
        try {
            SubjectResponseDTO createdSubject = subjectService.createSubject(subjectDTO);
            return ResponseEntity.ok(createdSubject);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating subject: " + e.getMessage());
        }
    }

    // Get all subjects
    @GetMapping("/")
    public ResponseEntity<List<SubjectResponseDTO>> getAllSubjects() {
        List<SubjectResponseDTO> subjects = subjectService.getAllSubjects();
        return ResponseEntity.ok(subjects);
    }

    // Get subject by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getSubjectById(@PathVariable Long id) {
        try {
            SubjectResponseDTO subject = subjectService.getSubjectById(id);
            return ResponseEntity.ok(subject);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Update subject
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSubject(@PathVariable Long id, @RequestBody SubjectDTO subjectDTO) {
        try {
            SubjectResponseDTO updatedSubject = subjectService.updateSubject(id, subjectDTO);
            return ResponseEntity.ok(updatedSubject);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating subject: " + e.getMessage());
        }
    }

    // Deactivate subject (soft delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deactivateSubject(@PathVariable Long id) {
        try {
            subjectService.deactivateSubject(id);
            return ResponseEntity.ok("Subject deactivated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deactivating subject: " + e.getMessage());
        }
    }

    // Search subjects
    @GetMapping("/search")
    public ResponseEntity<List<SubjectResponseDTO>> searchSubjects(@RequestParam String query) {
        List<SubjectResponseDTO> subjects = subjectService.searchSubjects(query);
        return ResponseEntity.ok(subjects);
    }
}