package com.assignment.axievaassignment.service.impl;

import com.assignment.axievaassignment.entity.Employee;
import com.assignment.axievaassignment.exceptions.GlobalException;
import com.assignment.axievaassignment.manager.InsuranceApiManager;
import com.assignment.axievaassignment.model.EmployeeInsuranceDetails;
import com.assignment.axievaassignment.model.InsuranceDetails;
import com.assignment.axievaassignment.repository.EmployeeRepository;
import com.assignment.axievaassignment.service.EmployeeService;
import com.assignment.axievaassignment.utils.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final AppConfig appConfig;
    private final InsuranceApiManager insuranceApiManager;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                                AppConfig appConfig,
                               InsuranceApiManager insuranceApiManager) {
        this.employeeRepository = employeeRepository;
        this.appConfig = appConfig;
        this.insuranceApiManager = insuranceApiManager;
    }

    /**
     * Method to get employee insurance details along with employee details
     *
     * @param empId employee id
     * @return EmployeeInsuranceDetails
     * @throws GlobalException if employee not found or error fetching insurance details from external service
     */
    @Override
    public EmployeeInsuranceDetails getInsuranceDetails(String empId) throws GlobalException {

        Employee employee = getEmployeeById(empId);
        InsuranceDetails insuranceDetails = insuranceApiManager.getInsuranceDetailsFromExternalService(empId);

        return EmployeeInsuranceDetails.builder()
                .empName(employee.getEmpName())
                .empId(employee.getEmpId())
                .empEmail(employee.getEmpEmail())
                .insuranceType(insuranceDetails.getInsuranceType())
                .isInsuranceEnrolled(insuranceDetails.getIsInsuranceEnrolled())
                .build();
    }

    /**
     * Method to get employee details by employee id
     *
     * @param empId employee id
     * @return Employee
     * @throws GlobalException if employee not found
     */
    private Employee getEmployeeById(String empId) throws GlobalException {
        return employeeRepository.findByEmpId(empId)
                .orElseThrow(() -> new GlobalException(
                        String.format("%s: %s", appConfig.getProperty("axieva.employee.not.found"), empId)
                ));
    }
}
