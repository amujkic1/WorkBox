package com.example.business.controller;

import com.example.business.dto.ProjectReportDTO;
import com.example.business.model.Project;
import com.example.business.service.ProjectService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/business")
public class ProjectReportController {
    private final ProjectService projectService;
    @Autowired
    private ModelMapper modelMapper;

    public ProjectReportController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/finished-projects")
    public ResponseEntity<List<ProjectReportDTO>> getFinishedProjectsReport() {
        List<Project> finishedProjects = projectService.getFinishedProjects();

        List<ProjectReportDTO> dtoList = finishedProjects.stream()
                .map(project -> modelMapper.map(project, ProjectReportDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtoList);

    }
}
