package com.teacherPortal.marksManagement.model;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Student {
@Id
@GeneratedValue(strategy =GenerationType.IDENTITY)
    private  Long id;
    private String firstName;
    private String lastName;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="student_id")
    private  List<Subject> subjects;
}
