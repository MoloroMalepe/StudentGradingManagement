package com.teacherPortal.marksManagement.repository;

import com.teacherPortal.marksManagement.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
