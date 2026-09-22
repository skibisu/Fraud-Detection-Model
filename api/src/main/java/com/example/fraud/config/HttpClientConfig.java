package com.example.fraud.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class HttpClientConfig {
    @Bean
    RestClient modelRestClient(@Value("${model.service-url}") String modelServiceUrl) {
        return RestClient.builder().baseUrl(modelServiceUrl).build();
    }
}

