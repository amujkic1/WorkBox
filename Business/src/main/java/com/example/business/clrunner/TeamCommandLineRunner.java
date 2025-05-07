package com.example.business.clrunner;

import com.example.business.model.Team;
import com.example.business.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(1)
@Component
public class TeamCommandLineRunner implements CommandLineRunner {

    @Autowired
    private TeamRepository teamRepository;

    @Override
    public void run(String... args) throws Exception {
        Team team1 = new Team();
        team1.setName("Razvojni Team");
        team1.setTeamLeader("Ajna");
        teamRepository.save(team1);

        Team team2 = new Team();
        team2.setName("Marketing Team");
        team2.setTeamLeader("Haso");
        teamRepository.save(team2);

        System.out.println("Teams added successfully");
    }
}

