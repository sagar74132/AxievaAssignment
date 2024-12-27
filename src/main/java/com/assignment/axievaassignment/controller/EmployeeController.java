package com.assignment.axievaassignment.controller;

import com.assignment.axievaassignment.exceptions.GlobalException;
import com.assignment.axievaassignment.model.EmployeeInsuranceDetails;
import com.assignment.axievaassignment.service.EmployeeService;
import com.assignment.axievaassignment.utils.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    private EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/insurance")
    public ResponseEntity<GenericResponse<EmployeeInsuranceDetails>> getInsuranceDetails(
            @RequestParam(value = "empId", required = true) String empId) throws GlobalException {

        GenericResponse<EmployeeInsuranceDetails> response = new GenericResponse<>();
        response.setMessage("Insurance details fetched successfully");
        response.setData(employeeService.getInsuranceDetails(empId));
        return ResponseEntity.ok(response);
    }
}
