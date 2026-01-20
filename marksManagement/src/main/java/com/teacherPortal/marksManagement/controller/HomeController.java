package com.teacherPortal.marksManagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return """
            🚀 Teacher Portal Backend is RUNNING!
            
            Available Endpoints:
            • GET  /api/       - API root
            • GET  /api/test   - Test endpoint
            • GET  /api/health - Health check
            • POST /api/auth/login - User login
            
            Time: %s
            """.formatted(new java.util.Date());
    }


    @GetMapping("/api/")
    public String apiRoot() {
        return """
            {
                "service": "Teacher Portal API",
                "description": "Student Grading Management System",
                "version": "1.0.0",
                "status": "operational",
                "timestamp": "%s",
                "endpoints": {
                    "health": "/api/health",
                    "auth": {
                        "login": "/api/auth/login"
                    }
                }
            }
            """.formatted(new java.util.Date());
    }

    @GetMapping("/api/health")
    public String health() {
        return """
            {
                "status": "UP",
                "service": "teacher-portal",
                "database": "connected",
                "timestamp": "%s"
            }
            """.formatted(new java.util.Date());
    }
    @PostMapping("/api/test-login")
    public String testLogin(@RequestBody String body) {
        return "Received: " + body;
    }
}