package com.example.business.service;

import com.example.business.exception.ProjectNotFoundException;
import com.example.business.exception.UserNotFoundException;
import com.example.business.model.Project;
import com.example.business.model.Task;
import com.example.business.model.User;
import com.example.business.repository.ProjectRepository;
import com.example.business.repository.TaskRepository;
import com.example.business.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ProjectRepository projectRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(int id) {
        return taskRepository.findById(id);
    }

    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    public void deleteTask(int id) {
        taskRepository.deleteById(id);
    }

    public List<Task> getTasksByUserId(Integer userId) {
        return taskRepository.findByUserId(userId);
    }

    @Transactional
    public List<Task> addTasksToProject(Integer projectId, List<Task> tasks) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));

        for (Task task : tasks) {
            task.setProject(project);
        }
        return taskRepository.saveAll(tasks);
    }

    public List<Task> getTasksByProjectId(Integer projectId) {
        return taskRepository.findByProjectId(projectId);
    }

}

