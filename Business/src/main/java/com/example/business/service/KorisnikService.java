package com.example.business.service;

import com.example.business.model.Korisnik;
import com.example.business.repository.KorisnikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KorisnikService {

    @Autowired
    private KorisnikRepository korisnikRepository;

    public List<Korisnik> getAllKorisnici() {
        return korisnikRepository.findAll();
    }

    public Optional<Korisnik> getKorisnikById(int id) {
        return korisnikRepository.findById(id);
    }

    public Korisnik saveKorisnik(Korisnik korisnik) {
        return korisnikRepository.save(korisnik);
    }

    public Korisnik updateKorisnik(int id, Korisnik korisnikDetails) {
        return korisnikRepository.findById(id).map(korisnik -> {
            korisnik.setIme(korisnikDetails.getIme());
            korisnik.setPrezime(korisnikDetails.getPrezime());
            korisnik.setUsername(korisnikDetails.getUsername());
            korisnik.setPassword(korisnikDetails.getPassword());
            korisnik.setTim(korisnikDetails.getTim());
            return korisnikRepository.save(korisnik);
        }).orElseThrow(() -> new RuntimeException("Korisnik not found"));
    }

    public void deleteKorisnik(int id) {
        korisnikRepository.deleteById(id);
    }
}

