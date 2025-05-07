package com.example.business.clrunner;

import com.example.business.model.Project;
import com.example.business.model.Team;
import com.example.business.repository.ProjectRepository;
import com.example.business.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Order(2)
@Component
public class ProjectCommandLineRunner implements CommandLineRunner {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private TeamRepository teamRepository;

    @Override
    public void run(String... args) throws Exception {
        Optional<Team> teamOpt = teamRepository.findById(1);
        Optional<Team> teamOpt2 = teamRepository.findById(2);
        if(teamOpt.isPresent() && teamOpt2.isPresent()) {
            Team team = teamOpt.get();
            Project project1 = new Project("Project A", new Date(), null, new Date(),null, "In progress", "Haso", "klijentA@example.com", team);
            projectRepository.save(project1);

            Team team2 = teamOpt2.get();
            Project project2 = new Project("Project B", new Date(), null, new Date(), null, "Finished", "Emina", "klijentB@example.com", team2);
            projectRepository.save(project2);

            System.out.println("Projects added successfully");
        }
        else {
            System.out.println("No available team");
        }
    }
}
