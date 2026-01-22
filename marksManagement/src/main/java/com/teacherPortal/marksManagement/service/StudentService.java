package com.teacherPortal.marksManagement.service;

import com.teacherPortal.marksManagement.model.Student;
import com.teacherPortal.marksManagement.model.dto.StudentDTO;
import com.teacherPortal.marksManagement.model.dto.StudentResponseDTO;
import com.teacherPortal.marksManagement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    //creating new student
    public StudentResponseDTO createStudent(StudentDTO studentDTO) {
        // Check if student number already exists
        if (studentRepository.existsByStudentNumber(studentDTO.getStudentNumber())) {
            throw new RuntimeException("Student number already exists: " + studentDTO.getStudentNumber());
        }
        // Create new student
        Student student = new Student();
        student.setStudentNumber(studentDTO.getStudentNumber());
        student.setFirstName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setDateOfBirth(studentDTO.getDateOfBirth());
        student.setEmail(studentDTO.getEmail());
        student.setPhoneNumber(studentDTO.getPhoneNumber());
        student.setAddress(studentDTO.getAddress());

        Student savedStudent = studentRepository.save(student);
        return convertToResponseDTO(savedStudent);

    }

    // Get all active students
    public List<StudentResponseDTO> getAllStudents() {
        return studentRepository.findByIsActiveTrue()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Get student by ID
    public StudentResponseDTO getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        return convertToResponseDTO(student);
    }

    // Get student by student number (for public lookup)
    public StudentResponseDTO getStudentByNumber(String studentNumber) {
        Student student = studentRepository.findByStudentNumber(studentNumber)
                .orElseThrow(() -> new RuntimeException("Student not found with number: " + studentNumber));
        return convertToResponseDTO(student);
    }

    // Update student
    public StudentResponseDTO updateStudent(Long id, StudentDTO studentDTO) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));

        // Check if new student number conflicts with others
        if (!student.getStudentNumber().equals(studentDTO.getStudentNumber())) {
            if (studentRepository.existsByStudentNumber(studentDTO.getStudentNumber())) {
                throw new RuntimeException("Student number already exists: " + studentDTO.getStudentNumber());
            }
        }

        // Update fields
        student.setStudentNumber(studentDTO.getStudentNumber());
        student.setFirstName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setDateOfBirth(studentDTO.getDateOfBirth());
        student.setEmail(studentDTO.getEmail());
        student.setPhoneNumber(studentDTO.getPhoneNumber());
        student.setAddress(studentDTO.getAddress());

        Student updatedStudent = studentRepository.save(student);
        return convertToResponseDTO(updatedStudent);
    }

    // Deactivate student (soft delete)
    public void deactivateStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));

        student.setIsActive(false);
        studentRepository.save(student);
    }

    // Search students by name
    public List<StudentResponseDTO> searchStudents(String searchTerm) {
        return studentRepository
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(searchTerm, searchTerm)
                .stream()
                .filter(Student::getIsActive)
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Helper method to convert Student to StudentResponseDTO
    private StudentResponseDTO convertToResponseDTO(Student student) {
        StudentResponseDTO dto = new StudentResponseDTO();
        dto.setId(student.getId());
        dto.setStudentNumber(student.getStudentNumber());
        dto.setFirstName(student.getFirstName());
        dto.setLastName(student.getLastName());
        dto.setFullName(student.getFullName());
        dto.setDateOfBirth(student.getDateOfBirth());
        dto.setEmail(student.getEmail());
        dto.setPhoneNumber(student.getPhoneNumber());
        dto.setAddress(student.getAddress());
        dto.setCreatedAt(student.getCreatedAt());
        dto.setIsActive(student.getIsActive());
        return dto;
    }


}
