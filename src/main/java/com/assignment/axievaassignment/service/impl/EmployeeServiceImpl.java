package com.assignment.axievaassignment.service.impl;

import com.assignment.axievaassignment.entity.Employee;
import com.assignment.axievaassignment.exceptions.GlobalException;
import com.assignment.axievaassignment.model.EmployeeInsuranceDetails;
import com.assignment.axievaassignment.model.InsuranceDetails;
import com.assignment.axievaassignment.repository.EmployeeRepository;
import com.assignment.axievaassignment.service.EmployeeService;
import com.assignment.axievaassignment.utils.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final AppConfig appConfig;
    private final RestTemplate restTemplate;

    @Autowired
    private EmployeeServiceImpl(EmployeeRepository employeeRepository,
                                AppConfig appConfig,
                                RestTemplateBuilder builder) {
        this.employeeRepository = employeeRepository;
        this.appConfig = appConfig;
        this.restTemplate = builder.build();
    }

    @Override
    public EmployeeInsuranceDetails getInsuranceDetails(String empId) throws GlobalException {
        Employee employee = getEmployeeById(empId);
        InsuranceDetails insuranceDetails = getInsuranceDetailsFromExternalService(empId);

        return EmployeeInsuranceDetails.builder()
                .empName(employee.getEmpName())
                .empId(employee.getEmpId())
                .empEmail(employee.getEmpEmail())
                .insuranceType(insuranceDetails.getInsuranceType())
                .isInsuranceEnrolled(insuranceDetails.getIsInsuranceEnrolled())
                .build();
    }

    private Employee getEmployeeById(String empId) throws GlobalException {
        return employeeRepository.findByEmpId(empId)
                .orElseThrow(() -> new GlobalException("Employee not found with id: " + empId));
    }

    private InsuranceDetails getInsuranceDetailsFromExternalService(String empId) throws GlobalException {

        String insuranceServiceUrl = appConfig.getProperty("third.party.mocked.api.url");
        Map<String, String> param = Map.of("empId", empId);

        try {
            return restTemplate.postForObject(insuranceServiceUrl, param, InsuranceDetails.class);
        } catch (Exception e) {
            throw new GlobalException("Error fetching insurance details from external service", e);
        }
    }
}
