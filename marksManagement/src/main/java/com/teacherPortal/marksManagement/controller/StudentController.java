package com.teacherPortal.marksManagement.controller;

import com.teacherPortal.marksManagement.model.Student;
import com.teacherPortal.marksManagement.model.Subject;
import com.teacherPortal.marksManagement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "http://localhost:5173")
public class StudentController {
@Autowired
    private StudentRepository studentRepository;

@GetMapping
    public  List<Student> getAllStudents(){
    return studentRepository.findAll();
}
@PostMapping
    public Student addStudent(@RequestBody Student student){
    if(student.getSubjects() !=null){
        for(Subject subject : student.getSubjects()){
            subject.calculateResults();
        }
    }
    return studentRepository.save(student);
}

}
