package com.example.auth.clrunner;

import com.example.auth.model.Role;
import com.example.auth.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class RoleCommandLineRunner implements CommandLineRunner {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {

            Role role1 = new Role("admin","all");
            roleRepository.save(role1);


            System.out.println("Role added successfully.");
    }
}
