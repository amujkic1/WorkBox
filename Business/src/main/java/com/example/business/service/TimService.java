package com.example.business.service;

import com.example.business.model.Tim;
import com.example.business.repository.TimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TimService {

    @Autowired
    private TimRepository timRepository;

    public Tim saveTim(Tim tim) {
        return timRepository.save(tim);
    }

    public List<Tim> getAllTimovi() {
        return timRepository.findAll();
    }

    public Optional<Tim> getTimById(int id) {
        return timRepository.findById(id);
    }

    public Tim updateTim(int id, Tim timDetails) {
        Optional<Tim> optionalTim = timRepository.findById(id);
        if (optionalTim.isPresent()) {
            Tim tim = optionalTim.get();
            tim.setNaziv(timDetails.getNaziv());
            tim.setVodja(timDetails.getVodja());
            return timRepository.save(tim);
        }
        return null;
    }

    public void deleteTim(int id) {
        timRepository.deleteById(id);
    }
}

