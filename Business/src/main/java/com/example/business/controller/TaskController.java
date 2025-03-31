package com.example.business.controller;

import com.example.business.controller.assembler.TaskModelAssembler;
import com.example.business.exception.TaskNotFoundException;
import com.example.business.model.Project;
import com.example.business.model.Task;
import com.example.business.model.User;
import com.example.business.repository.ProjectRepository;
import com.example.business.repository.TaskRepository;
import com.example.business.repository.UserRepository;
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
public class TaskController {
    private final TaskRepository taskRepository;
    private final TaskModelAssembler taskModelAssembler;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public TaskController(TaskRepository taskRepository, TaskModelAssembler taskModelAssembler, ProjectRepository projectRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.taskModelAssembler = taskModelAssembler;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/tasks")
    public CollectionModel<EntityModel<Task>> all() {
        List<EntityModel<Task>> tasks = taskRepository.findAll().stream()
                .map(taskModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(tasks, linkTo(methodOn(TeamController.class).all()).withSelfRel());
    }

    @GetMapping("/tasks/{id}")
    public EntityModel<Task> one(@PathVariable Integer id) {
        Task task = taskRepository.findById(id).orElseThrow(()->new TaskNotFoundException(id));
        return taskModelAssembler.toModel(task);
    }

    @PostMapping("/tasks")
    ResponseEntity<?>newTask(@RequestBody Task newTask) {
        Optional<Project> projectOpt = projectRepository.findById(newTask.getProject().getId());
        Optional<User> userOpt = userRepository.findById(newTask.getUser().getId());

        if (projectOpt.isEmpty() || userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid project or user ID");
        }

        newTask.setProject(projectOpt.get());
        newTask.setUser(userOpt.get());
        EntityModel<Task> entityModel = taskModelAssembler.toModel(taskRepository.save(newTask));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("tasks/{id}")
    ResponseEntity<?>replaceTask(@RequestBody Task newTask, @PathVariable Integer id) {
        Task updatedTask = taskRepository.findById(id)
                .map(task -> {
                    Optional<Project> projectOpt = projectRepository.findById(newTask.getProject().getId());
                    Optional<User> userOpt = userRepository.findById(newTask.getUser().getId());

                    task.setName(newTask.getName());
                    task.setDescription(newTask.getDescription());
                    task.setStartDate(newTask.getStartDate());
                    task.setEndDate(newTask.getEndDate());
                    task.setSubmissionDate(newTask.getSubmissionDate());
                    task.setStatus(newTask.getStatus());
                    if(userOpt.isPresent())
                    task.setUser(userOpt.get());
                    else return null;
                    if(projectOpt.isPresent())
                    task.setProject(projectOpt.get());
                    else return null;
                    return taskRepository.save(task);
                })
                .orElseGet(()->{
                    return taskRepository.save(newTask);
                });
        EntityModel<Task> entityModel = taskModelAssembler.toModel(updatedTask);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/tasks/{id}")
    ResponseEntity<?> deleteTask(@PathVariable Integer id) {
        taskRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
