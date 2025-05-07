package com.example.hr.controller;

import com.example.hr.dto.ProjectReportDTO;
import com.example.hr.service.ProjectReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/business")
public class ProjectReportController {
    private final ProjectReportService projectReportService;

    public ProjectReportController(ProjectReportService projectReportService) {
        this.projectReportService = projectReportService;
    }

    @GetMapping("/finished-projects")
    public ResponseEntity<List<ProjectReportDTO>> requestReport() {
        List<ProjectReportDTO> reports = projectReportService.fetchFinishedProjectsFromBusinessService();
        return ResponseEntity.ok(reports);
    }
}