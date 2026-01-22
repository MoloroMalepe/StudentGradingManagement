package com.teacherPortal.marksManagement.controller;

import com.teacherPortal.marksManagement.model.dto.StudentResponseDTO;
import com.teacherPortal.marksManagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public")
public class PublicController {

    @Autowired
private StudentService studentService;
@GetMapping("/students/{studentNumber}")
    public ResponseEntity<?> getStudentByNumber(@PathVariable String studentNumber) {
        try {
            StudentResponseDTO student = studentService.getStudentByNumber(studentNumber);
            return ResponseEntity.ok(student);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

//This will later be extended to show marks when we build marks system
@GetMapping("/students/{studentNumber}/marks")
    public ResponseEntity<?> getStudentMarks(@PathVariable String studentNumber) {
        try {
            // For now, just return student info
            StudentResponseDTO student = studentService.getStudentByNumber(studentNumber);
            return ResponseEntity.ok("Student found: " + student.getFullName() + ". Marks functionality coming soon!");
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


}
