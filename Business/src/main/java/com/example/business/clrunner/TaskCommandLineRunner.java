package com.example.business.clrunner;

import com.example.business.model.Korisnik;
import com.example.business.model.Projekat;
import com.example.business.model.Task;
import com.example.business.repository.KorisnikRepository;
import com.example.business.repository.ProjekatRepository;
import com.example.business.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class TaskCommandLineRunner implements CommandLineRunner {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private KorisnikRepository korisnikRepository;

    @Autowired
    private ProjekatRepository projekatRepository;

    @Override
    public void run(String... args) throws Exception {
        Optional<Korisnik> korisnikOpt = korisnikRepository.findById(1);
        Optional<Projekat> projekatOpt = projekatRepository.findById(1);

        if (korisnikOpt.isPresent() && projekatOpt.isPresent()) {
            Korisnik korisnik = korisnikOpt.get();
            Projekat projekat = projekatOpt.get();

            Task task1 = new Task(new Date(), new Date(), null, "Task 1", "Opis taska 1", "U toku", projekat, korisnik);
            taskRepository.save(task1);

            Task task2 = new Task(new Date(), new Date(), null, "Task 2", "Opis taska 2", "Zavrsen", projekat, korisnik);
            taskRepository.save(task2);

            System.out.println("Taskovi su uspjesno dodani u bazu.");
        } else {
            System.out.println("Nema dostupnog korisnika ili projekta u bazi, dodavanje taskova nije moguce.");
        }
    }
}
