package com.example.business.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoadTestController {

    private final RestTemplate restTemplate;

    public LoadTestController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/test-load")
    public Map<String, Integer> testLoadBalancing() {
        Map<String, Integer> responseCount = new HashMap<>();
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 100; i++) {
            String response = restTemplate.getForObject("http://localhost:8084/api/info", String.class);
            responseCount.merge(response, 1, Integer::sum);
        }

        long duration = System.currentTimeMillis() - startTime;
        responseCount.put("Ukupno vrijeme (ms)", (int) duration);

        return responseCount;
    }
}
