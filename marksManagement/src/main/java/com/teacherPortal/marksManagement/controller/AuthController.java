package com.teacherPortal.marksManagement.controller;

import com.teacherPortal.marksManagement.model.dto.LoginRequest;
import com.teacherPortal.marksManagement.model.dto.LoginResponse;
import com.teacherPortal.marksManagement.model.dto.UserDTO;
import com.teacherPortal.marksManagement.security.JwtUtil;
import com.teacherPortal.marksManagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"http://localhost:3000"})
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login( @RequestBody LoginRequest loginRequest) {
        try {
            var user = userService.authenticate(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
            );

            String token = jwtUtil.generateToken(user.getUsername(), user.getRole());

            LoginResponse response = new LoginResponse();
            response.setToken(token);
            response.setUsername(user.getUsername());
            response.setRole(user.getRole());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(401).body("Login failed: " + e.getMessage());
        }
    }

    @PostMapping("/admin/create-teacher")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createTeacher(@Valid @RequestBody UserDTO userDTO) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String adminUsername = auth.getName();

            var teacher = userService.createTeacher(userDTO, adminUsername);
            return ResponseEntity.ok("Teacher created: " + teacher.getUsername());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    @GetMapping("/test")
    public String testAuth() {
        return "Auth endpoint is working!";
    }

}