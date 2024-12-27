package com.assignment.axievaassignment.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;


/**
 * This class is used to get the properties from the application.properties file
 */
@Configuration
public class AppConfig {

    private final Environment environment;

    @Autowired
    public AppConfig(Environment environment) {
        this.environment = environment;
    }

    public String getProperty(String key) {
        return environment.getProperty(key);
    }
}
