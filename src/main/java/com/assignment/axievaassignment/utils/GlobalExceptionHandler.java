package com.assignment.axievaassignment.utils;

import com.assignment.axievaassignment.model.EmployeeInsuranceDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * This class is used to handle global exceptions
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    // Handled all exceptions so that we get the response always of type GenericResponse
    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericResponse<EmployeeInsuranceDetails>> handleEmployeeException(Exception ex) {
        GenericResponse<EmployeeInsuranceDetails> response = new GenericResponse<>();
        response.setMessage(ex.getMessage());
        response.setData(null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
