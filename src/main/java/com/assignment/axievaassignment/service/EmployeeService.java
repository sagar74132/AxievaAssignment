package com.assignment.axievaassignment.service;

import com.assignment.axievaassignment.exceptions.GlobalException;
import com.assignment.axievaassignment.model.EmployeeInsuranceDetails;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeService {

    EmployeeInsuranceDetails getInsuranceDetails(String empId) throws GlobalException;
}
