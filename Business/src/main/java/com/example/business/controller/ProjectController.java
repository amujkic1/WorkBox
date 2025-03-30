package com.example.business.controller;

import com.example.business.controller.assembler.ProjectModelAssembler;
import com.example.business.exception.ProjectNotFoundException;
import com.example.business.model.Project;
import com.example.business.model.Team;
import com.example.business.repository.ProjectRepository;
import com.example.business.repository.TeamRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ProjectController {
    private final ProjectRepository projectRepository;
    private final ProjectModelAssembler projectModelAssembler;
    private final TeamRepository teamRepository;

    public ProjectController(ProjectRepository projectRepository, ProjectModelAssembler projectModelAssembler, TeamRepository teamRepository) {
        this.projectRepository = projectRepository;
        this.projectModelAssembler = projectModelAssembler;
        this.teamRepository = teamRepository;
    }

    @GetMapping("/projects")
    public CollectionModel<EntityModel<Project>> all() {
        List<EntityModel<Project>> projects = projectRepository.findAll().stream()
                .map(projectModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(projects, linkTo(methodOn(TeamController.class).all()).withSelfRel());
    }

    @GetMapping("/projects/{id}")
    public EntityModel<Project> one(@PathVariable Integer id) {
        Project project = projectRepository.findById(id).orElseThrow(()->new ProjectNotFoundException(id));
        return projectModelAssembler.toModel(project);
    }

    @PostMapping("/projects")
    ResponseEntity<?> newProject(@RequestBody Project newProject) {
        if (newProject.getTeam() != null && newProject.getTeam().getId() != null) {
            Optional<Team> existingTeam = teamRepository.findById(newProject.getTeam().getId());
            if (existingTeam.isPresent()) {
                newProject.setTeam(existingTeam.get());
            } else {
                return ResponseEntity.badRequest().body("Invalid team ID: " + newProject.getTeam().getId());
            }
        } else {
            return ResponseEntity.badRequest().body("Team ID is required.");
        }
        EntityModel<Project> entityModel = projectModelAssembler.toModel(projectRepository.save(newProject));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("projects/{id}")
    ResponseEntity<?>replaceProject(@RequestBody Project newProject, @PathVariable Integer id) {
        Project updatedProject = projectRepository.findById(id)
                .map(project -> {
                    project.setTitle(newProject.getTitle());
                    project.setProjectManager(newProject.getProjectManager());
                    project.setClientContact(newProject.getClientContact());
                    project.setPublicationDate(newProject.getPublicationDate());
                    project.setTakeoverDate(newProject.getTakeoverDate());
                    project.setStartDate(newProject.getStartDate());
                    project.setEndDate(newProject.getEndDate());
                    if (newProject.getTeam() != null && newProject.getTeam().getId() != null) {
                        Optional<Team> existingTeam = teamRepository.findById(newProject.getTeam().getId());
                        if (existingTeam.isPresent()) {
                            project.setTeam(existingTeam.get());
                        } else {
                            return null;
                        }
                    }
                    return projectRepository.save(project);
                })
                .orElseGet(()->{
                    if (newProject.getTeam() != null && newProject.getTeam().getId() != null) {
                        Optional<Team> existingTeam = teamRepository.findById(newProject.getTeam().getId());
                        if (existingTeam.isPresent()) {
                            newProject.setTeam(existingTeam.get());
                        } else {
                            return null;
                        }
                    }
                    return projectRepository.save(newProject);
                });

        if (updatedProject == null) {
            return ResponseEntity.badRequest().body("Invalid team ID.");
        }

        EntityModel<Project> entityModel = projectModelAssembler.toModel(updatedProject);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/projects/{id}")
    ResponseEntity<?>deleteProject(@PathVariable Integer id) {
        projectRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
