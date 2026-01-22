package com.teacherPortal.marksManagement.service;

import com.teacherPortal.marksManagement.model.User;
import com.teacherPortal.marksManagement.model.dto.UserDTO;
import com.teacherPortal.marksManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // TEMPORARY: We'll add PasswordEncoder back later
    // @Autowired
    // private PasswordEncoder passwordEncoder;

    public User createTeacher(UserDTO userDTO, String adminUsername) {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new RuntimeException("Username already exists: " + userDTO.getUsername());
        }

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("Email already registered: " + userDTO.getEmail());
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword()); // Store plain text TEMPORARILY
        user.setEmail(userDTO.getEmail());
        user.setRole("TEACHER");
        user.setCreatedBy(adminUsername);
        user.setCreatedAt(LocalDateTime.now());
        user.setIsActive(true);

        return userRepository.save(user);
    }

    public User authenticate(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        // TEMPORARY: Plain text comparison
        if (!password.equals(user.getPassword())) {
            throw new RuntimeException("Invalid password for user: " + username);
        }

        if (!user.getIsActive()) {
            throw new RuntimeException("Account is deactivated: " + username);
        }

        return user;
    }

    public List<User> getTeachersByAdmin(String adminUsername) {
        return userRepository.findByCreatedBy(adminUsername);
    }

    public List<User> getAllTeachers() {
        return userRepository.findByRole("TEACHER");
    }

    public void createInitialAdmin() {
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin2");
            admin.setPassword("admin1234"); // Plain text TEMPORARILY
            admin.setEmail("admin@school.eddu");
            admin.setRole("ADMIN");
            admin.setCreatedBy("SYSTEM");
            admin.setCreatedAt(LocalDateTime.now());
            admin.setIsActive(true);
            userRepository.save(admin);
            System.out.println("Initial admin created: admin/admin123");
        }
    }
}