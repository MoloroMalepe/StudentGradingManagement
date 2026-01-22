package com.teacherPortal.marksManagement.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name="subjects")
public class Subject {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
@Column(unique = true, nullable = false)
private String subjectCode;

@Column(nullable = false)
   private String subjectName;

    private String description;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private Boolean isActive = true;

public Subject(){ this.createdAt=LocalDateTime.now();}
    public Subject(Long id, String subject_name, String subject_code) {
        this.id = id;
        this.subjectName = subject_name;
        this.subjectCode = subject_code;
  this.createdAt=LocalDateTime.now();
  this.isActive=true;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public Long getId() {
        return id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public String getDescription() {
        return description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSubjectName(String subject_name) {
        this.subjectName = subject_name;
    }

    public void setSubjectCode(String subject_code) {
        this.subjectCode = subject_code;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
