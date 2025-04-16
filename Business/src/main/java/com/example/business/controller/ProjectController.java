package com.example.business.controller;

import com.example.business.controller.assembler.ProjectModelAssembler;
import com.example.business.exception.ProjectNotFoundException;
import com.example.business.model.Project;
import com.example.business.model.Team;
import com.example.business.service.ProjectService;
import com.example.business.service.TeamService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;
    private final ProjectModelAssembler projectModelAssembler;
    private final TeamService teamService;
    private final ObjectMapper objectMapper;

    public ProjectController(ProjectService projectService, ProjectModelAssembler projectModelAssembler, TeamService teamService, ObjectMapper objectMapper) {
        this.projectService = projectService;
        this.projectModelAssembler = projectModelAssembler;
        this.teamService = teamService;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public CollectionModel<EntityModel<Project>> all() {
        List<EntityModel<Project>> projects = projectService.getAllProjects().stream()
                .map(projectModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(projects, linkTo(methodOn(ProjectController.class).all()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Project> one(@PathVariable Integer id) {
        Project project = projectService.getProjectById(id).orElseThrow(()->new ProjectNotFoundException(id));
        return projectModelAssembler.toModel(project);
    }

    @PostMapping
    ResponseEntity<?> newProject(@RequestBody @Valid Project newProject) {
        if (newProject.getTeam() != null && newProject.getTeam().getId() != null) {
            Optional<Team> existingTeam = teamService.getTeamById(newProject.getTeam().getId());
            if (existingTeam.isPresent()) {
                newProject.setTeam(existingTeam.get());
            } else {
                return ResponseEntity.badRequest().body("Invalid team ID: " + newProject.getTeam().getId());
            }
        } else {
            return ResponseEntity.badRequest().body("Team ID is required.");
        }
        EntityModel<Project> entityModel = projectModelAssembler.toModel(projectService.saveProject(newProject));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/{id}")
    ResponseEntity<?>replaceProject(@RequestBody @Valid Project newProject, @PathVariable Integer id) {
        Project updatedProject = projectService.getProjectById(id)
                .map(project -> {
                    project.setTitle(newProject.getTitle());
                    project.setProjectManager(newProject.getProjectManager());
                    project.setClientContact(newProject.getClientContact());
                    project.setPublicationDate(newProject.getPublicationDate());
                    project.setTakeoverDate(newProject.getTakeoverDate());
                    project.setStartDate(newProject.getStartDate());
                    project.setEndDate(newProject.getEndDate());
                    if (newProject.getTeam() != null && newProject.getTeam().getId() != null) {
                        Optional<Team> existingTeam = teamService.getTeamById(newProject.getTeam().getId());
                        if (existingTeam.isPresent()) {
                            project.setTeam(existingTeam.get());
                        } else {
                            return null;
                        }
                    }
                    return projectService.saveProject(project);
                })
                .orElseGet(()->{
                    if (newProject.getTeam() != null && newProject.getTeam().getId() != null) {
                        Optional<Team> existingTeam = teamService.getTeamById(newProject.getTeam().getId());
                        if (existingTeam.isPresent()) {
                            newProject.setTeam(existingTeam.get());
                        } else {
                            return null;
                        }
                    }
                    return projectService.saveProject(newProject);
                });

        if (updatedProject == null) {
            return ResponseEntity.badRequest().body("Invalid team ID.");
        }

        EntityModel<Project> entityModel = projectModelAssembler.toModel(updatedProject);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?>deleteProject(@PathVariable Integer id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<?> updateProject(@PathVariable Integer id, @RequestBody JsonPatch patch) {
        try {
            Project project = projectService.getProjectById(id).orElseThrow(()->new ProjectNotFoundException(id));
            Project projectPatched = applyPatchToProject(patch, project);
            projectService.saveProject(projectPatched);
            EntityModel<Project> entityModel = projectModelAssembler.toModel(projectPatched);
            return ResponseEntity
                    .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                    .body(entityModel);
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (ProjectNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    private Project applyPatchToProject(
            JsonPatch patch, Project targetProject) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(targetProject, JsonNode.class));
        return objectMapper.treeToValue(patched, Project.class);
    }
}
