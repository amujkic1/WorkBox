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
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
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
@Tag(name="ApplicationController", description = "API for applications management")
public class ApplicationController {

    private final ApplicationRepository applicationRepository;
    private final OpeningRepository openingRepository;
    private final ModelMapper modelMapper;
    private final ApplicationModelAssembler applicationModelAssembler;
    private final ApplicationService applicationService;

    public ApplicationController(ApplicationRepository applicationRepository, OpeningRepository openingRepository,
                                 ModelMapper modelMapper, ApplicationModelAssembler applicationModelAssembler, ApplicationService applicationService) {
        this.applicationRepository = applicationRepository;
        this.openingRepository = openingRepository;
        this.modelMapper = modelMapper;
        this.applicationModelAssembler = applicationModelAssembler;
        this.applicationService = applicationService;
    }

    @GetMapping("/applications")
    public CollectionModel<EntityModel<ApplicationDTO>> all() {
        List<EntityModel<ApplicationDTO>> applications = applicationRepository.findAll().stream()
                .map(application -> applicationModelAssembler.toModel(modelMapper.map(application, ApplicationDTO.class)))
                .collect(Collectors.toList());
        return CollectionModel.of(applications, linkTo(methodOn(ApplicationController.class).all()).withSelfRel());
    }

    @GetMapping("/applications/{id}")
    public EntityModel<ApplicationDTO> one(@PathVariable Integer id) {
        Application application = applicationRepository.findById(id).orElseThrow(() -> new ApplicationNotFoundException(id));
        ApplicationDTO applicationDTO = modelMapper.map(application, ApplicationDTO.class);
        return applicationModelAssembler.toModel(applicationDTO);
    }

    /*@PostMapping("/applications")
    public ResponseEntity<?> newApplication(@RequestBody ApplicationDTO applicationDTO) {
        Opening opening = openingRepository.findById(applicationDTO.getOpeningId())
                .orElseThrow(() -> new OpeningNotFoundException(applicationDTO.getOpeningId()));

        Application application = modelMapper.map(applicationDTO, Application.class);
        application.setOpening(opening);
        Application savedApplication = applicationRepository.save(application);
        ApplicationDTO savedApplicationDTO = modelMapper.map(savedApplication, ApplicationDTO.class);

        EntityModel<ApplicationDTO> entityModel = applicationModelAssembler.toModel(savedApplicationDTO);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }*/


    @PostMapping("/applications")
    public ResponseEntity<?> newApplication(@RequestBody ApplicationDTO applicationDTO) {
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
    public ResponseEntity<?> replaceApplication(@RequestBody ApplicationDTO applicationDTO, @PathVariable Integer id) {
        Application updatedApplication = applicationRepository.findById(id)
                .map(application -> {
                    Opening opening = openingRepository.findById(applicationDTO.getOpeningId())
                            .orElseThrow(() -> new OpeningNotFoundException(applicationDTO.getOpeningId()));
                    modelMapper.map(applicationDTO, application);
                    application.setOpening(opening);
                    return applicationRepository.save(application);
                })
                .orElseThrow(() -> new ApplicationNotFoundException(id));

        ApplicationDTO updatedApplicationDTO = modelMapper.map(updatedApplication, ApplicationDTO.class);
        EntityModel<ApplicationDTO> entityModel = applicationModelAssembler.toModel(updatedApplicationDTO);
        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/applications/{id}")
    public ResponseEntity<?> deleteApplication(@PathVariable Integer id) {
        applicationRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}