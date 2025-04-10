package com.example.business.repository;

import com.example.business.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {
    @Query("SELECT t FROM Team t WHERE t.teamLeader = :leader")
    List<Team> findTeamsByLeader(@Param("leader") String leader);
}
