package com.example.business.clrunner;

import com.example.business.model.Projekat;
import com.example.business.repository.ProjekatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ProjekatCommandLineRunner implements CommandLineRunner {

    @Autowired
    private ProjekatRepository projekatRepository;

    @Override
    public void run(String... args) throws Exception {
        Projekat projekat1 = new Projekat("Projekat A", new Date(), null, new Date(), null, "Haso", "klijentA@example.com", null);
        projekat1.setTim(null);
        projekatRepository.save(projekat1);

        Projekat projekat2 = new Projekat("Projekat B", new Date(), null, new Date(), null, "Emina", "klijentB@example.com", null);
        projekat2.setTim(null);
        projekatRepository.save(projekat2);

        System.out.println("Projekti su uspjesno dodani u bazu.");
    }
}
