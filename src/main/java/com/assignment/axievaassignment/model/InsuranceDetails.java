package com.assignment.axievaassignment.model;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class InsuranceDetails {

    /*
    POJO for third party API response
     */

    private String empId;
    private Boolean isInsuranceEnrolled;
    private String insuranceType;
}
