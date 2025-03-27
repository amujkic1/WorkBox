package com.example.demo.EmployeeBenefits;

import com.example.demo.User.User;
import com.example.demo.User.UserRepository;
import com.example.demo.User.UserRepositoryCommandLineRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class EmployeeBenefitsRepositoryCommandLineRunner implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(EmployeeBenefitsRepositoryCommandLineRunner.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public void run(String... args) throws Exception {
        User user = userRepository.findById(1);
        EmployeeBenefits beneficija = new EmployeeBenefits(1, "zdravstveno osiguranje", "ažurno", "Javno i privatno ZO", 1.5, user);
        employeeRepository.insert(beneficija);
        log.info("Korisnici dodani u bazu!");
    }

}
