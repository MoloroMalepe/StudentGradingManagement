package com.teacherPortal.marksManagement.model.dto;

public class AssignmentDTO {

    private Long studentId;      // Student ID
    private Long subjectId;      // Subject ID
    private Long teacherId;      // Teacher ID (user with role=TEACHER)
    private String enrolledDate; // Optional: "2024-01-22"

    // Getters and Setters
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public Long getSubjectId() { return subjectId; }
    public void setSubjectId(Long subjectId) { this.subjectId = subjectId; }

    public Long getTeacherId() { return teacherId; }
    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }

    public String getEnrolledDate() { return enrolledDate; }
    public void setEnrolledDate(String enrolledDate) { this.enrolledDate = enrolledDate; }
}