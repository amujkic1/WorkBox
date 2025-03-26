package com.example.business.service;

import com.example.business.model.Projekat;
import com.example.business.repository.ProjekatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjekatService {

    @Autowired
    private ProjekatRepository projekatRepository;

    public List<Projekat> getAllProjekti() {
        return projekatRepository.findAll();
    }

    public Optional<Projekat> getProjekatById(int id) {
        return projekatRepository.findById(id);
    }

    public Projekat saveProjekat(Projekat projekat) {
        return projekatRepository.save(projekat);
    }

    public Projekat updateProjekat(int id, Projekat projekatDetails) {
        return projekatRepository.findById(id).map(projekat -> {
            projekat.setNaslov(projekatDetails.getNaslov());
            projekat.setDatumObjave(projekatDetails.getDatumObjave());
            projekat.setDatumPreuzimanja(projekatDetails.getDatumPreuzimanja());
            projekat.setDatumPocetka(projekatDetails.getDatumPocetka());
            projekat.setDatumZavrsetka(projekatDetails.getDatumZavrsetka());
            projekat.setVodjaProjekta(projekatDetails.getVodjaProjekta());
            projekat.setKontaktKlijenta(projekatDetails.getKontaktKlijenta());
            projekat.setTim(projekatDetails.getTim());
            return projekatRepository.save(projekat);
        }).orElseThrow(() -> new RuntimeException("Projekat not found"));
    }

    public void deleteProjekat(int id) {
        projekatRepository.deleteById(id);
    }
}
