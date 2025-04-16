package com.example.business.controller;

import com.example.business.controller.assembler.TaskModelAssembler;
import com.example.business.exception.TaskNotFoundException;
import com.example.business.model.Project;
import com.example.business.model.Task;
import com.example.business.model.User;
import com.example.business.repository.ProjectRepository;
import com.example.business.repository.TaskRepository;
import com.example.business.repository.UserRepository;
import com.example.business.service.ProjectService;
import com.example.business.service.TaskService;
import com.example.business.service.UserService;
import jakarta.validation.Valid;
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
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final TaskModelAssembler taskModelAssembler;
    private final ProjectService projectService;
    private final UserService userService;

    public TaskController(TaskService taskService, TaskModelAssembler taskModelAssembler, ProjectService projectService, UserService userService) {
        this.taskService = taskService;
        this.taskModelAssembler = taskModelAssembler;
        this.projectService = projectService;
        this.userService = userService;
    }

    @GetMapping
    public CollectionModel<EntityModel<Task>> all() {
        List<EntityModel<Task>> tasks = taskService.getAllTasks().stream()
                .map(taskModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(tasks, linkTo(methodOn(TeamController.class).all()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Task> one(@PathVariable Integer id) {
        Task task = taskService.getTaskById(id).orElseThrow(()->new TaskNotFoundException(id));
        return taskModelAssembler.toModel(task);
    }

    @PostMapping
    ResponseEntity<?>newTask(@RequestBody @Valid Task newTask) {
        Optional<Project> projectOpt = projectService.getProjectById(newTask.getProject().getId());
        Optional<User> userOpt = userService.getUserById(newTask.getUser().getId());

        if (projectOpt.isEmpty() || userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid project or user ID");
        }

        newTask.setProject(projectOpt.get());
        newTask.setUser(userOpt.get());
        EntityModel<Task> entityModel = taskModelAssembler.toModel(taskService.saveTask(newTask));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/{id}")
    ResponseEntity<?>replaceTask(@RequestBody @Valid Task newTask, @PathVariable Integer id) {
        Task updatedTask = taskService.getTaskById(id)
                .map(task -> {
                    Optional<Project> projectOpt = projectService.getProjectById(newTask.getProject().getId());
                    Optional<User> userOpt = userService.getUserById(newTask.getUser().getId());

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
                    return taskService.saveTask(task);
                })
                .orElseGet(()->{
                    return taskService.saveTask(newTask);
                });
        EntityModel<Task> entityModel = taskModelAssembler.toModel(updatedTask);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteTask(@PathVariable Integer id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getTasksByUser(@PathVariable Integer userId) {
        List<Task> tasks = taskService.getTasksByUserId(userId);

        if (tasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<Task>> taskModels = tasks.stream()
                .map(taskModelAssembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(taskModels,
                linkTo(methodOn(TaskController.class).getTasksByUser(userId)).withSelfRel()));
    }

}
