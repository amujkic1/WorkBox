package com.example.hr.controller;

import com.example.hr.assembler.OpeningModelAssembler;
import com.example.hr.dto.OpeningDTO;
import com.example.hr.exception.ApplicationNotFoundException;
import com.example.hr.exception.OpeningNotFoundException;
import com.example.hr.exception.UserNotFoundException;
import com.example.hr.model.Opening;
import com.example.hr.model.User;
import com.example.hr.repository.ApplicationRepository;
import com.example.hr.repository.OpeningRepository;
import com.example.hr.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@Tag(name="OpeningController", description = "API for openings management")
public class OpeningController {

    private final OpeningRepository openingRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final OpeningModelAssembler openingModelAssembler;
    private final ApplicationRepository applicationRepository;

    public OpeningController(OpeningRepository openingRepository, UserRepository userRepository,
                             ModelMapper modelMapper, OpeningModelAssembler openingModelAssembler,
                             ApplicationRepository applicationRepository) {
        this.openingRepository = openingRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.openingModelAssembler = openingModelAssembler;
        this.applicationRepository = applicationRepository;
    }

    /*@GetMapping("/openings")
    @Operation(summary = "Retrieve all openings", description = "Returns a list of all job openings")
    public CollectionModel<EntityModel<OpeningDTO>> all() {
        List<EntityModel<OpeningDTO>> openings = openingRepository.findAll().stream()
                .map(opening -> openingModelAssembler.toModel(modelMapper.map(opening, OpeningDTO.class)))
                .collect(Collectors.toList());
        return CollectionModel.of(openings, linkTo(methodOn(ApplicationController.class).all()).withSelfRel());
    }*/

    @GetMapping("/openings")
    @Operation(summary = "Retrieve all openings", description = "Returns a list of all job openings")
    public CollectionModel<EntityModel<OpeningDTO>> all() {
        List<EntityModel<OpeningDTO>> openings = openingRepository.findAll().stream()
            .map(opening -> {
                OpeningDTO dto = modelMapper.map(opening, OpeningDTO.class);
                dto.setApplicationCount((Integer) opening.getApplications().size()); // ručno dodaj broj aplikacija
                return openingModelAssembler.toModel(dto);
            })
            .collect(Collectors.toList());
        return CollectionModel.of(openings, linkTo(methodOn(ApplicationController.class).all()).withSelfRel());
    }

    @GetMapping("/openings/{id}")
    @Operation(summary = "Retrieve an opening by ID", description = "Fetches details of a specific opening by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Opening found"),
            @ApiResponse(responseCode = "404", description = "Opening not found")
    })
    public EntityModel<OpeningDTO> one(@PathVariable Integer id) {
        Opening opening = openingRepository.findById(id).orElseThrow(() -> new OpeningNotFoundException(id));
        OpeningDTO openingDTO = modelMapper.map(opening, OpeningDTO.class);
        return openingModelAssembler.toModel(openingDTO);
    }

    @PostMapping("/openings")
    @Operation(summary = "Create a new opening", description = "Adds a new opening to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Opening created"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<?> createOpening(@Valid @RequestBody OpeningDTO openingDTO) {
        User user = userRepository.findById(openingDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException(openingDTO.getUserId()));

        Opening newOpening = modelMapper.map(openingDTO, Opening.class);
        newOpening.setUser(user);
        Opening savedOpening = openingRepository.save(newOpening);

        OpeningDTO savedOpeningDTO = modelMapper.map(savedOpening, OpeningDTO.class);
        EntityModel<OpeningDTO> entityModel = openingModelAssembler.toModel(savedOpeningDTO);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/openings/{id}")
    @Operation(summary = "Update an opening", description = "Updates an existing job opening by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Opening updated"),
            @ApiResponse(responseCode = "404", description = "Opening not found")
    })
    public ResponseEntity<?> replaceOpening(@Valid @RequestBody OpeningDTO openingDTO, @PathVariable Integer id){
        Opening updatedOpening = openingRepository.findById(id)
                .map(opening -> {
                    if(openingDTO.getOpeningName() != null){
                        opening.setOpeningName(openingDTO.getOpeningName());
                    }
                    if(openingDTO.getDescription() != null){
                        opening.setDescription(openingDTO.getDescription());
                    }
                    if(openingDTO.getConditions() != null){
                        opening.setConditions(openingDTO.getConditions());
                    }
                    if(openingDTO.getBenefits() != null){
                        opening.setBenefits(openingDTO.getBenefits());
                    }
                    if(openingDTO.getStartDate() != null){
                        opening.setStartDate(openingDTO.getStartDate());
                    }
                    if(openingDTO.getEndDate() != null){
                        opening.setEndDate(openingDTO.getEndDate());
                    }
                    if(openingDTO.getResult() != null){
                        opening.setResult(openingDTO.getResult());
                    }
                    return openingRepository.save(opening);
                }).orElseThrow(() -> new OpeningNotFoundException(id));

        OpeningDTO updatedOpeningDTO = modelMapper.map(updatedOpening, OpeningDTO.class);
        EntityModel<OpeningDTO> entityModel = openingModelAssembler.toModel(updatedOpeningDTO);
        return ResponseEntity.ok(entityModel);

    }

    @Transactional
    @DeleteMapping("/openings/{id}")
    @Operation(summary = "Delete an opening", description = "Deletes a job opening by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Opening deleted"),
            @ApiResponse(responseCode = "404", description = "Opening not found")
    })
    public ResponseEntity<?> deleteOpening(@PathVariable Integer id) {
        Optional<Opening> openingOptional = openingRepository.findById(id);
        if (openingOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Opening with ID " + id + " not found.");
        }

        Opening opening = openingOptional.get();

        // brisanje iz medjutabele
        for (User user : opening.getUsers()) {
            user.getOpenings().remove(opening);
        }

        opening.getUsers().clear();

        openingRepository.save(opening);

        opening.getApplications().clear();

        openingRepository.delete(opening);

        return ResponseEntity.ok("Opening with ID " + id + " has been deleted successfully.");
    }


}