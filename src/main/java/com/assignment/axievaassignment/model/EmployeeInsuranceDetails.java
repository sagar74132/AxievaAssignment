package com.assignment.axievaassignment.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter@Setter
public class EmployeeInsuranceDetails {

    private String empId;
    private String empName;
    private String empEmail;
    private Boolean isInsuranceEnrolled;
    private String insuranceType;
}
