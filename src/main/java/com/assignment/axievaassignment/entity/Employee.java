package com.assignment.axievaassignment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Employee {
    @Id
    private String empId;

    @Column
    private String empName;

    @Column
    private String empEmail;
}
