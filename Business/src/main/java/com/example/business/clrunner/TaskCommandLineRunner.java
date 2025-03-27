package com.example.business.clrunner;

import com.example.business.model.Project;
import com.example.business.model.User;
import com.example.business.model.Task;
import com.example.business.repository.UserRepository;
import com.example.business.repository.ProjectRepository;
import com.example.business.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Order(5)
@Component
public class TaskCommandLineRunner implements CommandLineRunner {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public void run(String... args) throws Exception {
        Optional<User> userOpt = userRepository.findById(1);
        Optional<Project> projectOpt = projectRepository.findById(1);

        if (userOpt.isPresent() && projectOpt.isPresent()) {
            User user = userOpt.get();
            Project project = projectOpt.get();

            Task task1 = new Task(new Date(), new Date(), null, "Task 1", "Opis taska 1", "U toku", project, user);
            taskRepository.save(task1);

            Task task2 = new Task(new Date(), new Date(), null, "Task 2", "Opis taska 2", "Zavrsen", project, user);
            taskRepository.save(task2);

            System.out.println("Tasks added successfully");
        } else {
            System.out.println("No available user or project");
        }
    }
}
