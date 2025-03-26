package com.example.business.clrunner;

import com.example.business.model.Korisnik;
import com.example.business.model.Tim;
import com.example.business.repository.KorisnikRepository;
import com.example.business.repository.TimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class KorisnikCommandLineRunner implements CommandLineRunner {

    @Autowired
    private KorisnikRepository korisnikRepository;

    @Autowired
    private TimRepository timRepository;

    @Override
    public void run(String... args) throws Exception {
        Tim tim = timRepository.findById(1).orElse(null);

        if (tim != null) {
            Korisnik korisnik1 = new Korisnik("Emina", "Peljto", "epeljto1", "password123", tim);
            korisnikRepository.save(korisnik1);

            Korisnik korisnik2 = new Korisnik("Ajna", "Mujkic", "amujkic1", "securePass456", tim);
            korisnikRepository.save(korisnik2);

            System.out.println("Korisnici su uspjesno dodani u bazu.");
        } else {
            System.out.println("Nema dostupnog tima u bazi, dodavanje korisnika nije moguce.");
        }
    }
}

