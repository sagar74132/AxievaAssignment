package com.assignment.axievaassignment.service;

import com.assignment.axievaassignment.entity.Employee;
import com.assignment.axievaassignment.exceptions.GlobalException;
import com.assignment.axievaassignment.model.EmployeeInsuranceDetails;
import com.assignment.axievaassignment.model.InsuranceDetails;
import com.assignment.axievaassignment.repository.EmployeeRepository;
import com.assignment.axievaassignment.service.impl.EmployeeServiceImpl;
import com.assignment.axievaassignment.utils.AppConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private AppConfig appConfig;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private RestTemplateBuilder restTemplateBuilder;

    private EmployeeServiceImpl employeeService;

    private Employee testEmployee;
    private InsuranceDetails testInsuranceDetails;
    private static final String TEST_EMP_ID = "PS0001";
    private static final String MOCK_API_URL = "http://mock-insurance-api.com";

    @BeforeEach
    void setUp() {
        when(restTemplateBuilder.build()).thenReturn(restTemplate);

        employeeService = new EmployeeServiceImpl(employeeRepository, appConfig, restTemplateBuilder);

        // Setup test employee
        testEmployee = new Employee();
        testEmployee.setEmpId(TEST_EMP_ID);
        testEmployee.setEmpName("Kumar Sanu");
        testEmployee.setEmpEmail("sanu@xyz.com");

        // Setup test insurance details
        testInsuranceDetails = new InsuranceDetails();
        testInsuranceDetails.setInsuranceType("HEALTH");
        testInsuranceDetails.setIsInsuranceEnrolled(true);

    }

    @Test
    void getInsuranceDetails_Success() throws GlobalException {
        // Arrange
        when(employeeRepository.findByEmpId(TEST_EMP_ID)).thenReturn(Optional.of(testEmployee));
        // Setup AppConfig mock
        when(appConfig.getProperty("third.party.mocked.api.url")).thenReturn(MOCK_API_URL);
        when(restTemplate.postForObject(
                eq(MOCK_API_URL),
                any(Map.class),
                eq(InsuranceDetails.class)
        )).thenReturn(testInsuranceDetails);

        // Act
        EmployeeInsuranceDetails result = employeeService.getInsuranceDetails(TEST_EMP_ID);

        // Assert
        assertNotNull(result);
        assertEquals(TEST_EMP_ID, result.getEmpId());
        assertEquals(testEmployee.getEmpName(), result.getEmpName());
        assertEquals(testEmployee.getEmpEmail(), result.getEmpEmail());
        assertEquals(testInsuranceDetails.getInsuranceType(), result.getInsuranceType());
        assertEquals(testInsuranceDetails.getIsInsuranceEnrolled(), result.getIsInsuranceEnrolled());

        // Verify interactions
        verify(employeeRepository).findByEmpId(TEST_EMP_ID);
        verify(restTemplate).postForObject(
                eq(MOCK_API_URL),
                any(java.util.Map.class),
                eq(InsuranceDetails.class)
        );
    }

    @Test
    void getInsuranceDetails_EmployeeNotFound() {
        // Arrange
        when(employeeRepository.findByEmpId(TEST_EMP_ID)).thenReturn(Optional.empty());
        when(appConfig.getProperty("axieva.employee.not.found")).thenReturn("Employee not found with id");

        // Act & Assert
        GlobalException exception = assertThrows(GlobalException.class,
                () -> employeeService.getInsuranceDetails(TEST_EMP_ID));

        assertEquals("Employee not found with id: " + TEST_EMP_ID, exception.getMessage());
        verify(employeeRepository).findByEmpId(TEST_EMP_ID);
        verify(restTemplate, never()).postForObject(
                any(String.class),
                any(Object.class),
                any(Class.class)
        );
    }

    @Test
    void getInsuranceDetails_InsuranceServiceError() {
        // Arrange
        when(employeeRepository.findByEmpId(TEST_EMP_ID)).thenReturn(Optional.of(testEmployee));
        // Setup AppConfig mock
        when(appConfig.getProperty("third.party.mocked.api.url")).thenReturn(MOCK_API_URL);
        when(restTemplate.postForObject(
                eq(MOCK_API_URL),
                any(java.util.Map.class),
                eq(InsuranceDetails.class)
        )).thenThrow(new RuntimeException("Service unavailable"));

        when(appConfig.getProperty("error.fetching.insurance.details"))
                .thenReturn("Error fetching insurance details");

        // Act & Assert
        GlobalException exception = assertThrows(GlobalException.class,
                () -> employeeService.getInsuranceDetails(TEST_EMP_ID));

        assertEquals("Error fetching insurance details", exception.getMessage());
        verify(employeeRepository).findByEmpId(TEST_EMP_ID);
        verify(restTemplate).postForObject(
                eq(MOCK_API_URL),
                any(java.util.Map.class),
                eq(InsuranceDetails.class)
        );
    }

    @Test
    void getInsuranceDetails_NullEmployeeId() {
        // Act & Assert
        assertThrows(GlobalException.class,
                () -> employeeService.getInsuranceDetails(null));

        verify(employeeRepository).findByEmpId(null);
        verify(restTemplate, never()).postForObject(
                any(String.class),
                any(Object.class),
                any(Class.class)
        );
    }

    @Test
    void getInsuranceDetails_EmptyEmployeeId() {
        // Act & Assert
        assertThrows(GlobalException.class,
                () -> employeeService.getInsuranceDetails(""));

        verify(employeeRepository).findByEmpId("");
        verify(restTemplate, never()).postForObject(
                any(String.class),
                any(Object.class),
                any(Class.class)
        );
    }

}
