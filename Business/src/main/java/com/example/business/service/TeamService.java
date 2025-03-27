package com.example.business.service;

import com.example.business.model.Team;
import com.example.business.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    public Team saveTim(Team team) {
        return teamRepository.save(team);
    }

    public List<Team> getAllTimovi() {
        return teamRepository.findAll();
    }

    public Optional<Team> getTimById(Integer id) {
        return teamRepository.findById(id);
    }


    public void deleteTim(Integer id) {
        teamRepository.deleteById(id);
    }
}

