package com.example.hr.controller;

import com.example.hr.dto.ProjectReportDTO;
import com.example.hr.service.ProjectReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/business")
@Tag(name = "ProjectReportController", description = "API for business reports and project statistics")
public class ProjectReportController {
    private final ProjectReportService projectReportService;

    public ProjectReportController(ProjectReportService projectReportService) {
        this.projectReportService = projectReportService;
    }

    @GetMapping("/finished-projects")
    @Operation(summary = "Retrieve finished projects report", description = "Fetches a report of all completed projects from the business service")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved finished projects report"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<ProjectReportDTO>> requestReport() {
        List<ProjectReportDTO> reports = projectReportService.fetchFinishedProjectsFromBusinessService();
        return ResponseEntity.ok(reports);
    }

}