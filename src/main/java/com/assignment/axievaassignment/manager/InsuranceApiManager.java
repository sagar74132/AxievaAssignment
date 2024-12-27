package com.assignment.axievaassignment.manager;

import com.assignment.axievaassignment.constant.Constant;
import com.assignment.axievaassignment.exceptions.GlobalException;
import com.assignment.axievaassignment.model.InsuranceDetails;
import com.assignment.axievaassignment.utils.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class InsuranceApiManager {

    private final RestTemplate restTemplate;
    private final AppConfig appConfig;

    @Autowired
    public InsuranceApiManager(RestTemplateBuilder builder,
                               AppConfig appConfig) {

        this.restTemplate = builder.build();
        this.appConfig = appConfig;
    }

    /**
     * Method to get insurance details from external service
     *
     * @param empId employee id
     * @return InsuranceDetails
     * @throws GlobalException if error fetching insurance details from external service
     */
    public InsuranceDetails getInsuranceDetailsFromExternalService(String empId) throws GlobalException {

        String insuranceServiceUrl = appConfig.getProperty("third.party.mocked.api.url");

        // Created a payload with the required param
        Map<String, String> payload = Map.of(
                Constant.THIRD_PARTY_URL_PARAM, empId
        );

        try {
            return restTemplate.postForObject(insuranceServiceUrl, payload, InsuranceDetails.class);
        } catch (Exception e) {
            throw new GlobalException(String.format("%s: %s", appConfig.getProperty("error.fetching.insurance.details"), e));
        }
    }
}
