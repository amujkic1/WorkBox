package com.example.business.controller;

import com.example.business.controller.assembler.TeamModelAssembler;
import com.example.business.exception.TeamNotFoundException;
import com.example.business.model.Team;
import com.example.business.repository.TeamRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class TeamController {
    private final TeamRepository teamRepository;
    private  final TeamModelAssembler teamModelAssembler;

    public TeamController(TeamRepository teamRepository, TeamModelAssembler teamModelAssembler) {
        this.teamRepository = teamRepository;
        this.teamModelAssembler = teamModelAssembler;
    }

    @GetMapping("/teams")
    public CollectionModel<EntityModel<Team>>all() {
        List<EntityModel<Team>> teams = teamRepository.findAll().stream()
                .map(teamModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(teams, linkTo(methodOn(TeamController.class).all()).withSelfRel());
    }

    @GetMapping("/teams/{id}")
    public EntityModel<Team> one(@PathVariable Integer id) {
        Team team = teamRepository.findById(id).orElseThrow(()->new TeamNotFoundException(id));
        return teamModelAssembler.toModel(team);
    }
}
