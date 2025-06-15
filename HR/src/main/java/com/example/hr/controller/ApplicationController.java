package com.example.hr.controller;

import com.example.hr.assembler.ApplicationModelAssembler;
import com.example.hr.dto.ApplicationDTO;
import com.example.hr.exception.ApplicationNotFoundException;
import com.example.hr.exception.OpeningNotFoundException;
import com.example.hr.model.Application;
import com.example.hr.model.Opening;
import com.example.hr.repository.ApplicationRepository;
import com.example.hr.repository.OpeningRepository;
import com.example.hr.service.ApplicationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
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
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@Tag(name="ApplicationController", description = "API for applications management")
public class ApplicationController {

    private final ApplicationRepository applicationRepository;
    private final OpeningRepository openingRepository;
    private final ModelMapper modelMapper;
    private final ApplicationModelAssembler applicationModelAssembler;
    private final ApplicationService applicationService;
    private final ObjectMapper objectMapper;

    public ApplicationController(ApplicationRepository applicationRepository, OpeningRepository openingRepository,
                                 ModelMapper modelMapper, ApplicationModelAssembler applicationModelAssembler, ApplicationService applicationService,
                                 ObjectMapper objectMapper) {
        this.applicationRepository = applicationRepository;
        this.openingRepository = openingRepository;
        this.modelMapper = modelMapper;
        this.applicationModelAssembler = applicationModelAssembler;
        this.applicationService = applicationService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/applications")
    @Operation(summary = "Retrieve all applications", description = "Returns a list of all job applications")
    public CollectionModel<EntityModel<ApplicationDTO>> all() {
        List<EntityModel<ApplicationDTO>> applications = applicationRepository.findAll().stream()
                .map(application -> applicationModelAssembler.toModel(modelMapper.map(application, ApplicationDTO.class)))
                .collect(Collectors.toList());
        return CollectionModel.of(applications, linkTo(methodOn(ApplicationController.class).all()).withSelfRel());
    }

    @GetMapping("/applications/{id}")
    @Operation(summary = "Retrieve an application by ID", description = "Fetches details of a specific application by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Application found"),
            @ApiResponse(responseCode = "404", description = "Application not found")
    })
    public EntityModel<ApplicationDTO> one(@PathVariable Integer id) {
        Application application = applicationRepository.findById(id).orElseThrow(() -> new ApplicationNotFoundException(id));
        ApplicationDTO applicationDTO = modelMapper.map(application, ApplicationDTO.class);
        return applicationModelAssembler.toModel(applicationDTO);
    }

    @PostMapping("/applications")
    @Operation(summary = "Create a new application", description = "Adds a new application to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Application created"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<?> newApplication(@Valid @RequestBody ApplicationDTO applicationDTO) {
        Opening opening = openingRepository.findById(applicationDTO.getOpeningId())
                .orElseThrow(() -> new OpeningNotFoundException(applicationDTO.getOpeningId()));

        Application application = modelMapper.map(applicationDTO, Application.class);
        application.setOpening(opening);
        Application savedApplication = applicationService.insert(application);
        ApplicationDTO savedApplicationDTO = modelMapper.map(savedApplication, ApplicationDTO.class);

        EntityModel<ApplicationDTO> entityModel = applicationModelAssembler.toModel(savedApplicationDTO);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PutMapping("/applications/{id}")
    @Operation(summary = "Update an application", description = "Updates an existing job application by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OApplication updated"),
            @ApiResponse(responseCode = "404", description = "Application not found")
    })
    public ResponseEntity<?> replaceApplication(@Valid @RequestBody ApplicationDTO applicationDTO, @PathVariable Integer id) {
        Application updatedApplication = applicationRepository.findById(id)
                .map(application -> {
                    if (applicationDTO.getFirstName() != null) {
                        application.setFirstName(applicationDTO.getFirstName());
                    }
                    if (applicationDTO.getLastName() != null) {
                        application.setLastName(applicationDTO.getLastName());
                    }
                    if (applicationDTO.getEmail() != null) {
                        application.setEmail(applicationDTO.getEmail());
                    }
                    if (applicationDTO.getContactNumber() != null) {
                        application.setContactNumber(applicationDTO.getContactNumber());
                    }
                    if (applicationDTO.getDocumentationLink() != null) {
                        application.setDocumentationLink(applicationDTO.getDocumentationLink());
                    }
                    if (applicationDTO.getStatus() != null) {
                        application.setStatus(applicationDTO.getStatus());
                    }
                    if (applicationDTO.getPoints() != null) {
                        application.setPoints(applicationDTO.getPoints());
                    }
                    if (applicationDTO.getOpeningId() != null) {
                        Opening opening = openingRepository.findById(applicationDTO.getOpeningId())
                                .orElseThrow(() -> new OpeningNotFoundException(applicationDTO.getOpeningId()));
                        application.setOpening(opening);
                    }

                    return applicationRepository.save(application);
                })
                .orElseThrow(() -> new ApplicationNotFoundException(id));

        ApplicationDTO updatedApplicationDTO = modelMapper.map(updatedApplication, ApplicationDTO.class);
        EntityModel<ApplicationDTO> entityModel = applicationModelAssembler.toModel(updatedApplicationDTO);
        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/applications/{id}")
    @Operation(summary = "Delete an application", description = "Deletes a job application by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Application deleted"),
            @ApiResponse(responseCode = "404", description = "Application not found")
    })
    public ResponseEntity<?> deleteApplication(@PathVariable Integer id) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException(id));

        applicationRepository.delete(application);

        return ResponseEntity.ok().body(Collections.singletonMap("message", "Application successfully deleted."));
    }


    @PatchMapping(path = "/applications/{id}", consumes = "application/json-patch+json")
    @Operation(summary = "Partially update an application", description = "Applies a JSON Patch to an application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Application patched"),
            @ApiResponse(responseCode = "404", description = "Application not found"),
            @ApiResponse(responseCode = "500", description = "Patch processing failed")
    })
    public ResponseEntity<?> updateApplication(@PathVariable Integer id, @RequestBody JsonPatch patch) {
        try {
            Application application = applicationRepository.findById(id)
                    .orElseThrow(() -> new ApplicationNotFoundException(id));
            ApplicationDTO applicationDTO = modelMapper.map(application, ApplicationDTO.class);

            ApplicationDTO patchedDTO = applyPatchToDTO(patch, applicationDTO);

            Opening opening = openingRepository.findById(patchedDTO.getOpeningId())
                    .orElseThrow(() -> new OpeningNotFoundException(patchedDTO.getOpeningId()));

            modelMapper.map(patchedDTO, application);
            application.setOpening(opening);

            Application savedApplication = applicationService.update(application);
            ApplicationDTO savedDTO = modelMapper.map(savedApplication, ApplicationDTO.class);

            EntityModel<ApplicationDTO> entityModel = applicationModelAssembler.toModel(savedDTO);
            return ResponseEntity.ok(entityModel);

        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Patch error: " + e.getMessage());
        }
    }

    private ApplicationDTO applyPatchToDTO(JsonPatch patch, ApplicationDTO targetDTO) throws JsonPatchException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode patched = patch.apply(objectMapper.convertValue(targetDTO, JsonNode.class));
        return objectMapper.treeToValue(patched, ApplicationDTO.class);
    }

    @GetMapping("/applications/sorted")
    @Operation(summary = "Get applications sorted by points", description = "Returns applications sorted by their points in descending order")
    public CollectionModel<EntityModel<ApplicationDTO>> getApplicationsSortedByPoints() {
        List<EntityModel<ApplicationDTO>> applications = applicationService.findAllOrderByPointsDesc().stream()
                .map(application -> modelMapper.map(application, ApplicationDTO.class))
                .map(applicationModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(applications, linkTo(methodOn(ApplicationController.class).getApplicationsSortedByPoints()).withSelfRel());
    }


}