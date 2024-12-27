package com.assignment.axievaassignment.utils;

import com.assignment.axievaassignment.model.EmployeeInsuranceDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericResponse<EmployeeInsuranceDetails>> handleEmployeeException(Exception ex) {
        GenericResponse<EmployeeInsuranceDetails> response = new GenericResponse<>();
        response.setMessage(ex.getMessage());
        response.setData(null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
