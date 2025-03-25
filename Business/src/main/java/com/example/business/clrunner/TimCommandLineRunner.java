package com.example.business.clrunner;

import com.example.business.model.Tim;
import com.example.business.repository.TimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TimCommandLineRunner implements CommandLineRunner {

    @Autowired
    private TimRepository timRepository;

    @Override
    public void run(String... args) throws Exception {
        // Kreiranje tima
        Tim tim1 = new Tim();
        tim1.setNaziv("Razvojni Tim");
        tim1.setVodja("Ajna");
        timRepository.save(tim1);

        Tim tim2 = new Tim();
        tim2.setNaziv("Marketing Tim");
        tim2.setVodja("Haso");
        timRepository.save(tim2);

        System.out.println("Timovi su uspjesno dodani u bazu.");
    }
}

