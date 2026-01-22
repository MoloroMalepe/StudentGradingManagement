package com.teacherPortal.marksManagement.service;

import com.teacherPortal.marksManagement.model.Subject;
import com.teacherPortal.marksManagement.model.dto.SubjectDTO;
import com.teacherPortal.marksManagement.model.dto.SubjectResponseDTO;
import com.teacherPortal.marksManagement.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    // Create new subject
    public SubjectResponseDTO createSubject(SubjectDTO subjectDTO) {
        // Check if subject code already exists
        if (subjectRepository.existsBySubjectCode(subjectDTO.getSubjectCode())) {
            throw new RuntimeException("Subject code already exists: " + subjectDTO.getSubjectCode());
        }

        // Create new subject
        Subject subject = new Subject();
        subject.setSubjectCode(subjectDTO.getSubjectCode());
        subject.setSubjectName(subjectDTO.getSubjectName());
        subject.setDescription(subjectDTO.getDescription());

        Subject savedSubject = subjectRepository.save(subject);
        return convertToResponseDTO(savedSubject);
    }

    // Get all active subjects
    public List<SubjectResponseDTO> getAllSubjects() {
        return subjectRepository.findByIsActiveTrue()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Get subject by ID
    public SubjectResponseDTO getSubjectById(Long id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found with id: " + id));
        return convertToResponseDTO(subject);
    }

    // Get subject by subject code
    public SubjectResponseDTO getSubjectByCode(String subjectCode) {
        Subject subject = subjectRepository.findBySubjectCode(subjectCode)
                .orElseThrow(() -> new RuntimeException("Subject not found with code: " + subjectCode));
        return convertToResponseDTO(subject);
    }

    // Update subject
    public SubjectResponseDTO updateSubject(Long id, SubjectDTO subjectDTO) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found with id: " + id));

        // Check if new subject code conflicts with others
        if (!subject.getSubjectCode().equals(subjectDTO.getSubjectCode())) {
            if (subjectRepository.existsBySubjectCode(subjectDTO.getSubjectCode())) {
                throw new RuntimeException("Subject code already exists: " + subjectDTO.getSubjectCode());
            }
        }

        // Update fields
        subject.setSubjectCode(subjectDTO.getSubjectCode());
        subject.setSubjectName(subjectDTO.getSubjectName());
        subject.setDescription(subjectDTO.getDescription());

        Subject updatedSubject = subjectRepository.save(subject);
        return convertToResponseDTO(updatedSubject);
    }

    // Deactivate subject (soft delete)
    public void deactivateSubject(Long id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found with id: " + id));

        subject.setIsActive(false);
        subjectRepository.save(subject);
    }

    // Search subjects
    public List<SubjectResponseDTO> searchSubjects(String searchTerm) {
        return subjectRepository
                .findBySubjectCodeContainingIgnoreCaseOrSubjectNameContainingIgnoreCase(
                        searchTerm, searchTerm)
                .stream()
                .filter(Subject::getIsActive)
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Helper method to convert Subject to SubjectResponseDTO
    private SubjectResponseDTO convertToResponseDTO(Subject subject) {
        SubjectResponseDTO dto = new SubjectResponseDTO();
        dto.setId(subject.getId());
        dto.setSubjectCode(subject.getSubjectCode());
        dto.setSubjectName(subject.getSubjectName());
        dto.setDescription(subject.getDescription());
        dto.setCreatedAt(subject.getCreatedAt());
        dto.setIsActive(subject.getIsActive());
        return dto;
    }
}