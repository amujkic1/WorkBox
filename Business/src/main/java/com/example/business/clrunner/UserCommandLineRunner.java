package com.example.business.clrunner;

import com.example.business.model.User;
import com.example.business.model.Team;
import com.example.business.repository.UserRepository;
import com.example.business.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(4)
@Component
public class UserCommandLineRunner implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Override
    public void run(String... args) throws Exception {
        Team team = teamRepository.findById(1).orElse(null);

        if (team != null) {
            User user1 = new User("Emina", "Peljto", team);
            userRepository.save(user1);

            User user2 = new User("Ajna", "Mujkic", team);
            userRepository.save(user2);

            System.out.println("Users added successfully");
        } else {
            System.out.println("No available team");
        }
    }
}

