package com.teacherPortal.marksManagement.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "student_subjects", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"student_id", "subject_id"})
})
public class StudentSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private User teacher;  // Teacher from users table (role=TEACHER)

    private LocalDate enrolledDate;

    private LocalDateTime createdAt;

    private Boolean isActive = true;

    // Constructors
    public StudentSubject() {
        this.createdAt = LocalDateTime.now();
        this.enrolledDate = LocalDate.now();
    }

    public StudentSubject(Student student, Subject subject, User teacher) {
        this.student = student;
        this.subject = subject;
        this.teacher = teacher;
        this.createdAt = LocalDateTime.now();
        this.enrolledDate = LocalDate.now();
        this.isActive = true;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Subject getSubject() { return subject; }
    public void setSubject(Subject subject) { this.subject = subject; }

    public User getTeacher() { return teacher; }
    public void setTeacher(User teacher) { this.teacher = teacher; }

    public LocalDate getEnrolledDate() { return enrolledDate; }
    public void setEnrolledDate(LocalDate enrolledDate) { this.enrolledDate = enrolledDate; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}