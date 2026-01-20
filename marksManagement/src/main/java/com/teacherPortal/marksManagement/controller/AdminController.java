package com.teacherPortal.marksManagement.controller;

import com.teacherPortal.marksManagement.model.User;
import com.teacherPortal.marksManagement.model.dto.UserDTO;
import com.teacherPortal.marksManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
//@PreAuthorize("hasRole('ADMIN')") // Only admins can access
public class AdminController {

    @Autowired
    private UserService userService;

    // Helper to get current admin username
    private String getCurrentAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
    // Create teacher account
    @PostMapping("/create-teacher")
    public ResponseEntity<?> createTeacher(
            @RequestBody UserDTO userDTO) {
        try {
            String adminUsername = getCurrentAdmin();
            User teacher = userService.createTeacher(userDTO, adminUsername);
            return ResponseEntity.ok("Teacher created: " + teacher.getUsername());
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error: "+e.getMessage());
        }
    }

    // View all teachers created by this admin
    @GetMapping("/teachers")
    public ResponseEntity<?> getTeachers() {
        try{
            String adminUsername = getCurrentAdmin();
        List<User> teachers = userService.getTeachersByAdmin(adminUsername);
        return ResponseEntity.ok(teachers);} catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: "+e.getMessage());
        }
    }

    @GetMapping("/all-teachers")
    public ResponseEntity<?> getAllTeachers() {
        try {
            List<User> teachers = userService.getAllTeachers();
            return ResponseEntity.ok(teachers);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}