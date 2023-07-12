package com.example.vacancyimportservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class QuotaService {

    @Value("${quota.url}")
    private String url;

    public boolean isQuota() {
        log.debug("Request to rate limiter");
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response
                = restTemplate.getForEntity(url, String.class);

        return response.getStatusCode() == HttpStatus.OK;
    }
}
