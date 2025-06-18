package com.example.business.controller;

import com.example.business.controller.assembler.TeamModelAssembler;
import com.example.business.exception.TeamNotFoundException;
import com.example.business.model.Team;
import com.example.business.service.TeamService;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/teams")
public class TeamController {
    private  final TeamModelAssembler teamModelAssembler;
    private final TeamService teamService;


    public TeamController(TeamService teamService, TeamModelAssembler teamModelAssembler) {
        this.teamService = teamService;
        this.teamModelAssembler = teamModelAssembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Team>>all() {
        List<EntityModel<Team>> teams = teamService.getAllTeams().stream()
                .map(teamModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(teams, linkTo(methodOn(TeamController.class).all()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Team> one(@PathVariable Integer id) {
        Team team = teamService.getTeamById(id).orElseThrow(()->new TeamNotFoundException(id));
        return teamModelAssembler.toModel(team);
    }

    @PostMapping
    ResponseEntity<?>newTeam(@RequestBody @Valid Team newTeam) {
        EntityModel<Team> entityModel = teamModelAssembler.toModel(teamService.saveTeam(newTeam));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/{id}")
    ResponseEntity<?>replaceTeam(@RequestBody @Valid Team newTeam, @PathVariable Integer id) {
        Team updatedTeam = teamService.getTeamById(id)
                .map(team -> {
                    team.setName(newTeam.getName());
                    team.setTeamLeader(newTeam.getTeamLeader());
                    return teamService.saveTeam(team);
                })
                .orElseGet(()->{
                    return teamService.saveTeam(newTeam);
                });
        EntityModel<Team> entityModel = teamModelAssembler.toModel(updatedTeam);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?>deleteTeam(@PathVariable Integer id) {
        teamService.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }
}
