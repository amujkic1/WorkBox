package com.example.business;

import com.example.business.model.Team;
import com.example.business.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class TeamServiceIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TeamRepository teamRepository;

    private Team preloadedTeam;

    @BeforeEach
    void setup() {
        Team team = new Team();
        team.setName("Preloaded Team");
        team.setTeamLeader("Leader Before Each");

        preloadedTeam = entityManager.persist(team);
        entityManager.flush();
    }

    @Test
    void testSaveAndFindTeam() {
        Team team = new Team();
        team.setName("Test Team");
        team.setTeamLeader("Test Leader");

        Team saved = entityManager.persist(team);
        entityManager.flush();

        Team found = teamRepository.findById(saved.getId()).orElse(null);
        assertNotNull(found);
        assertEquals("Test Team", found.getName());
        assertEquals("Test Leader", found.getTeamLeader());
    }

    @Test
    void testFindPreloadedTeam() {
        Team found = teamRepository.findById(preloadedTeam.getId()).orElse(null);
        assertNotNull(found);
        assertEquals("Preloaded Team", found.getName());
        assertEquals("Leader Before Each", found.getTeamLeader());
    }

    @Test
    void testFindAllTeams() {
        List<Team> teams = teamRepository.findAll();
        assertFalse(teams.isEmpty());
        assertTrue(teams.stream().anyMatch(t -> t.getName().equals("Preloaded Team")));
    }

    @Test
    void testUpdateTeam() {
        Team team = teamRepository.findById(preloadedTeam.getId()).orElse(null);
        assertNotNull(team);

        team.setTeamLeader("Updated Leader");
        teamRepository.save(team);

        Team updated = teamRepository.findById(preloadedTeam.getId()).orElse(null);
        assertNotNull(updated);
        assertEquals("Updated Leader", updated.getTeamLeader());
    }

    @Test
    void testDeleteTeam() {
        teamRepository.deleteById(preloadedTeam.getId());
        Team deleted = teamRepository.findById(preloadedTeam.getId()).orElse(null);
        assertNull(deleted);
    }

    @Test
    void testTeamNotFound() {
        Team notFound = teamRepository.findById(9999).orElse(null);
        assertNull(notFound);
    }
}
