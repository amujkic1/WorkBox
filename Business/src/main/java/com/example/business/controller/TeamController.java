package com.example.business.controller;

import com.example.business.controller.assembler.TeamModelAssembler;
import com.example.business.exception.TeamNotFoundException;
import com.example.business.model.Team;
import com.example.business.repository.TeamRepository;
import com.example.business.service.TeamService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/teams")
public class TeamController {
    private final TeamRepository teamRepository;
    private final TeamService teamService;
    private  final TeamModelAssembler teamModelAssembler;
    private final ObjectMapper objectMapper;

    public TeamController(TeamRepository teamRepository, TeamService teamService, TeamModelAssembler teamModelAssembler, ObjectMapper objectMapper) {
        this.teamRepository = teamRepository;
        this.teamService = teamService;
        this.teamModelAssembler = teamModelAssembler;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public CollectionModel<EntityModel<Team>>all() {
        List<EntityModel<Team>> teams = teamRepository.findAll().stream()
                .map(teamModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(teams, linkTo(methodOn(TeamController.class).all()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Team> one(@PathVariable Integer id) {
        Team team = teamRepository.findById(id).orElseThrow(()->new TeamNotFoundException(id));
        return teamModelAssembler.toModel(team);
    }

    @PostMapping
    ResponseEntity<?>newTeam(@RequestBody Team newTeam) {
        EntityModel<Team> entityModel = teamModelAssembler.toModel(teamRepository.save(newTeam));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/{id}")
    ResponseEntity<?>replaceTeam(@RequestBody Team newTeam, @PathVariable Integer id) {
        Team updatedTeam = teamRepository.findById(id)
                .map(team -> {
                    team.setName(newTeam.getName());
                    team.setTeamLeader(newTeam.getTeamLeader());
                    return teamRepository.save(team);
                })
                .orElseGet(()->{
                    return teamRepository.save(newTeam);
                });
        EntityModel<Team> entityModel = teamModelAssembler.toModel(updatedTeam);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?>deleteTeam(@PathVariable Integer id) {
        teamRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public @PatchMapping(path = "/{id}",consumes = "application/json-patch+json")
    ResponseEntity<?> updateTeam(@PathVariable Integer id,@RequestBody JsonPatch patch) throws JsonProcessingException {
        try {
            Team team = teamRepository.findById(id).orElseThrow(() -> new TeamNotFoundException(id));
            Team teamPatched = applyPatchToTeam(patch, team);
            teamRepository.save(teamPatched);
            EntityModel<Team> entityModel = teamModelAssembler.toModel(teamPatched);
            return ResponseEntity
                    .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                    .body(entityModel);
    } catch (JsonPatchException | JsonProcessingException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}

    private Team applyPatchToTeam(JsonPatch patch, Team targetTeam) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(targetTeam, JsonNode.class));
        return objectMapper.treeToValue(patched, Team.class);
    }

    @GetMapping("/leader/{name}")
    public CollectionModel<EntityModel<Team>> getTeamsByLeader(@PathVariable String name) {
        List<EntityModel<Team>> teams = teamService.getTeamsByLeader(name).stream()
                .map(teamModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(teams, linkTo(methodOn(TeamController.class).all()).withSelfRel());
    }

    }
