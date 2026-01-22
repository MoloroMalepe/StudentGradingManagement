package com.teacherPortal.marksManagement.controller;

import com.teacherPortal.marksManagement.model.dto.StudentDTO;
import com.teacherPortal.marksManagement.model.dto.StudentResponseDTO;
import com.teacherPortal.marksManagement.service.StudentService;
import com.teacherPortal.marksManagement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/students")
@PreAuthorize("hasRole('ADMIN')")
//@CrossOrigin(origins = "http://localhost:5173")

public class StudentController {
@Autowired
    private StudentService studentService;


//create new student
@PostMapping("/")
    public ResponseEntity<?> createStudent(@RequestBody StudentDTO studentDTO){
        try {
            StudentResponseDTO createdStudent = studentService.createStudent(studentDTO);
            return ResponseEntity.ok(createdStudent);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating student: " + e.getMessage());
        }
    }

    // Get all students
    @GetMapping("/")
    public ResponseEntity<List<StudentResponseDTO>> getAllStudents() {
        List<StudentResponseDTO> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    //getting student by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable Long id) {
        try {
            StudentResponseDTO student = studentService.getStudentById(id);
            return ResponseEntity.ok(student);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Update student
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @RequestBody StudentDTO studentDTO) {
        try {
            StudentResponseDTO updatedStudent = studentService.updateStudent(id, studentDTO);
            return ResponseEntity.ok(updatedStudent);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating student: " + e.getMessage());
        }
    }

    // Deactivate student (soft delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deactivateStudent(@PathVariable Long id) {
        try {
            studentService.deactivateStudent(id);
            return ResponseEntity.ok("Student deactivated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deactivating student: " + e.getMessage());
        }
    }

    // Search students
    @GetMapping("/search")
    public ResponseEntity<List<StudentResponseDTO>> searchStudents(@RequestParam String query) {
        List<StudentResponseDTO> students = studentService.searchStudents(query);
        return ResponseEntity.ok(students);
    }



}
