package com.teacherPortal.marksManagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Subject {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double testMark;
    private double testWeight;
    private double examMark;
    private double examWeight;

    private double finalMark;
    private String status;      // "PASS" or "FAIL"

    public void calculateResults() {
        this.finalMark = (testMark * testWeight) + (examMark * examWeight);
        this.status = (this.finalMark >= 40) ? "PASS" : "FAIL";
    }
}
