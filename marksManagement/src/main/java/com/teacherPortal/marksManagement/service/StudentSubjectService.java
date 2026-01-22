package com.teacherPortal.marksManagement.service;

import com.teacherPortal.marksManagement.model.*;
import com.teacherPortal.marksManagement.model.dto.AssignmentDTO;
import com.teacherPortal.marksManagement.model.dto.AssignmentResponseDTO;
import com.teacherPortal.marksManagement.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentSubjectService {

    @Autowired
    private StudentSubjectRepository studentSubjectRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private UserRepository userRepository;

    // Create new assignment
    public AssignmentResponseDTO createAssignment(AssignmentDTO assignmentDTO) {
        // Check if assignment already exists
        if (studentSubjectRepository.existsByStudentIdAndSubjectId(
                assignmentDTO.getStudentId(), assignmentDTO.getSubjectId())) {
            throw new RuntimeException("Student is already enrolled in this subject");
        }

        // Get student
        Student student = studentRepository.findById(assignmentDTO.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + assignmentDTO.getStudentId()));

        // Get subject
        Subject subject = subjectRepository.findById(assignmentDTO.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found with id: " + assignmentDTO.getSubjectId()));

        // Get teacher (user with role TEACHER)
        User teacher = userRepository.findById(assignmentDTO.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + assignmentDTO.getTeacherId()));

        // Verify teacher role
        if (!"TEACHER".equals(teacher.getRole())) {
            throw new RuntimeException("User with id " + assignmentDTO.getTeacherId() + " is not a teacher");
        }

        // Create assignment
        StudentSubject assignment = new StudentSubject();
        assignment.setStudent(student);
        assignment.setSubject(subject);
        assignment.setTeacher(teacher);

        // Set enrolled date if provided
        if (assignmentDTO.getEnrolledDate() != null && !assignmentDTO.getEnrolledDate().isEmpty()) {
            assignment.setEnrolledDate(LocalDate.parse(assignmentDTO.getEnrolledDate()));
        }

        StudentSubject savedAssignment = studentSubjectRepository.save(assignment);
        return convertToResponseDTO(savedAssignment);
    }

    // Get all assignments
    public List<AssignmentResponseDTO> getAllAssignments() {
        return studentSubjectRepository.findByIsActiveTrue()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Get assignments by student
    public List<AssignmentResponseDTO> getAssignmentsByStudent(Long studentId) {
        return studentSubjectRepository.findByStudentId(studentId)
                .stream()
                .filter(StudentSubject::getIsActive)
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Get assignments by teacher
    public List<AssignmentResponseDTO> getAssignmentsByTeacher(Long teacherId) {
        return studentSubjectRepository.findByTeacherId(teacherId)
                .stream()
                .filter(StudentSubject::getIsActive)
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Get assignments by subject
    public List<AssignmentResponseDTO> getAssignmentsBySubject(Long subjectId) {
        return studentSubjectRepository.findBySubjectId(subjectId)
                .stream()
                .filter(StudentSubject::getIsActive)
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Deactivate assignment
    public void deactivateAssignment(Long id) {
        StudentSubject assignment = studentSubjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assignment not found with id: " + id));

        assignment.setIsActive(false);
        studentSubjectRepository.save(assignment);
    }

    // Helper method to convert to Response DTO
    private AssignmentResponseDTO convertToResponseDTO(StudentSubject assignment) {
        AssignmentResponseDTO dto = new AssignmentResponseDTO();
        dto.setId(assignment.getId());

        // Student info
        dto.setStudentId(assignment.getStudent().getId());
        dto.setStudentName(assignment.getStudent().getFullName());
        dto.setStudentNumber(assignment.getStudent().getStudentNumber());

        // Subject info
        dto.setSubjectId(assignment.getSubject().getId());
        dto.setSubjectCode(assignment.getSubject().getSubjectCode());
        dto.setSubjectName(assignment.getSubject().getSubjectName());

        // Teacher info
        dto.setTeacherId(assignment.getTeacher().getId());
        dto.setTeacherName(assignment.getTeacher().getUsername()); // Using username as name
        dto.setTeacherUsername(assignment.getTeacher().getUsername());

        // Dates
        dto.setEnrolledDate(assignment.getEnrolledDate());
        dto.setCreatedAt(assignment.getCreatedAt());
        dto.setIsActive(assignment.getIsActive());

        return dto;
    }
}