package com.example.demo.clrunner;

import com.example.demo.models.EmployeeBenefit;
import com.example.demo.models.User;
import com.example.demo.service.EmployeeBenefitService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
public class EmployeeBenefitCommandLineRunner implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeBenefitService employeeBenefitService;

    @Override
    public void run(String... args) throws Exception {
        User user = userService.getAllUsers().getFirst();
        EmployeeBenefit benfit = new EmployeeBenefit("zdravstvo","aktivno","Zdravstveno osiguranje",user);
        employeeBenefitService.insert(benfit);
        System.out.println("Unesen benefit");
    }
}
