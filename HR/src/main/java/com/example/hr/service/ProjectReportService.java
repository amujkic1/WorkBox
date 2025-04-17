package com.example.hr.service;

import com.example.hr.dto.ProjectReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ProjectReportService {

    private final RestTemplate restTemplate;

    @Autowired
    public ProjectReportService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<ProjectReportDTO> fetchFinishedProjectsFromBusinessService() {
        String url = "http://business-service/business/finished-projects";

        ResponseEntity<ProjectReportDTO[]> response = restTemplate.getForEntity(
                url, ProjectReportDTO[].class
        );

        return Arrays.asList(response.getBody());
    }
}