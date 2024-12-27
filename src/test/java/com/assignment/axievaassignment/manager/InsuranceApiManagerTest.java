package com.assignment.axievaassignment.manager;

import com.assignment.axievaassignment.exceptions.GlobalException;
import com.assignment.axievaassignment.model.InsuranceDetails;
import com.assignment.axievaassignment.utils.AppConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InsuranceApiManagerTest {

    @Mock
    private AppConfig appConfig;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private RestTemplateBuilder restTemplateBuilder;

    private InsuranceApiManager insuranceApiManager;

    private InsuranceDetails testInsuranceDetails;
    private static final String TEST_EMP_ID = "PS0001";
    private static final String MOCK_API_URL = "http://mock-insurance-api.com";

    @BeforeEach
    void setUp() {
        when(restTemplateBuilder.build()).thenReturn(restTemplate);
        when(appConfig.getProperty("third.party.mocked.api.url")).thenReturn(MOCK_API_URL);

        insuranceApiManager = new InsuranceApiManager(restTemplateBuilder, appConfig);

        // Setup test insurance details
        testInsuranceDetails = new InsuranceDetails();
        testInsuranceDetails.setEmpId(TEST_EMP_ID);
        testInsuranceDetails.setInsuranceType("HEALTH");
        testInsuranceDetails.setIsInsuranceEnrolled(true);
    }

    @Test
    void getInsuranceDetails_Success() throws GlobalException {
        // Arrange
        when(restTemplate.postForObject(
                eq(MOCK_API_URL),
                any(Map.class),
                eq(InsuranceDetails.class)
        )).thenReturn(testInsuranceDetails);

        // Act
        InsuranceDetails result = insuranceApiManager.getInsuranceDetailsFromExternalService(TEST_EMP_ID);

        // Assert
        assertEquals(TEST_EMP_ID, result.getEmpId());
        assertEquals("HEALTH", result.getInsuranceType());
        assertEquals(true, result.getIsInsuranceEnrolled());

        // Verify
        verify(restTemplate).postForObject(
                eq(MOCK_API_URL),
                any(Map.class),
                eq(InsuranceDetails.class)
        );
    }

    @Test
    void getInsuranceDetails_Failure() throws GlobalException {
        // Arrange
        when(restTemplate.postForObject(
                eq(MOCK_API_URL),
                any(Map.class),
                eq(InsuranceDetails.class)
        )).thenThrow(RestClientException.class);

        // Act
        assertThrows(GlobalException.class,
                () -> insuranceApiManager.getInsuranceDetailsFromExternalService(TEST_EMP_ID));
    }
}
