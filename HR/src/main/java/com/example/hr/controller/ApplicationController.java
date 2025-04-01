package com.example.hr.controller;

import com.example.hr.dto.ApplicationDTO;
import com.example.hr.exception.ApplicationNotFoundException;
import com.example.hr.exception.OpeningNotFoundException;
import com.example.hr.model.Application;
import com.example.hr.model.Opening;
import com.example.hr.repository.ApplicationRepository;
import com.example.hr.repository.OpeningRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Tag(name="ApplicationController", description = "API for applications management")
public class ApplicationController {

    private final ApplicationRepository applicationRepository;
    private final OpeningRepository openingRepository;

    public ApplicationController(ApplicationRepository applicationRepository, OpeningRepository openingRepository) {
        this.applicationRepository = applicationRepository;
        this.openingRepository = openingRepository;
    }

    @GetMapping("/applications")
    @Operation(summary = "Retrieve all applications", description = "Returns a list of all job applications")
    List<ApplicationDTO> getAllApplications(){
        return applicationRepository.findAll().stream()
                .map(application -> new ApplicationDTO(
                        application.getDate(),
                        application.getFirstName(),
                        application.getLastName(),
                        application.getEmail(),
                        application.getContactNumber(),
                        application.getDocumentationLink(),
                        application.getStatus(),
                        application.getPoints(),
                        application.getOpening().getId()
                )).collect(Collectors.toList());
    }

    @GetMapping("/application/{id}")
    @Operation(summary = "Retrieve an application by ID", description = "Fetches details of a specific application by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Application found"),
            @ApiResponse(responseCode = "404", description = "Application not found")
    })
    @ExceptionHandler(ApplicationNotFoundException.class)
    ApplicationDTO getApplicationById(@PathVariable Integer id){
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException(id));

        return new ApplicationDTO(
                application.getDate(),
                application.getFirstName(),
                application.getLastName(),
                application.getEmail(),
                application.getContactNumber(),
                application.getDocumentationLink(),
                application.getStatus(),
                application.getPoints(),
                application.getOpening().getId()
        );
    }

    @PostMapping("/application")
    @Operation(summary = "Create a new application", description = "Adds a new job application to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Application created"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<String> createApplication(@RequestBody ApplicationDTO applicationDTO) {
        Opening opening = openingRepository.findById(applicationDTO.getOpeningId())
                .orElseThrow(() -> new OpeningNotFoundException(applicationDTO.getOpeningId()));

        Application application = new Application(
                applicationDTO.getDate(),
                applicationDTO.getFirstName(),
                applicationDTO.getLastName(),
                applicationDTO.getEmail(),
                applicationDTO.getContactNumber(),
                applicationDTO.getDocumentationLink(),
                applicationDTO.getStatus(),
                applicationDTO.getPoints(),
                opening
        );

        Application savedApplication = applicationRepository.save(application);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Application successfully created.");

        /*return new ApplicationDTO(
                savedApplication.getId(),
                savedApplication.getDate(),
                savedApplication.getFirstName(),
                savedApplication.getLastName(),
                savedApplication.getEmail(),
                savedApplication.getContactNumber(),
                savedApplication.getDocumentationLink(),
                savedApplication.getStatus(),
                savedApplication.getPoints(),
                savedApplication.getOpening().getId()
        );*/

    }

    /*
@PostMapping("/application")
public ResponseEntity<Map<String, String>> createApplication(@RequestBody ApplicationDTO applicationDTO) {
    try {
        // Provjera da li postoji Opening
        Opening opening = openingRepository.findById(applicationDTO.getOpeningId())
                .orElseThrow(() -> new OpeningNotFoundException(applicationDTO.getOpeningId()));

        // Kreiranje nove prijave
        Application application = new Application(
                applicationDTO.getDate(),
                applicationDTO.getFirstName(),
                applicationDTO.getLastName(),
                applicationDTO.getEmail(),
                applicationDTO.getContactNumber(),
                applicationDTO.getDocumentationLink(),
                applicationDTO.getStatus(),
                applicationDTO.getPoints(),
                opening
        );

        applicationRepository.save(application);

        // Uspješan odgovor (201 Created)
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "Application successfully created."));

    } catch (OpeningNotFoundException e) {
        // Ako Opening ne postoji, vraćamo 404 Not Found
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Opening with ID " + applicationDTO.getOpeningId() + " not found."));

    } catch (Exception e) {
        // Opšta greška (400 Bad Request)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", "Invalid input data. Please check your request."));
    }
}

    * */

    @PutMapping("/application/{id}")
    @Operation(summary = "Update an application", description = "Updates an existing job application by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Application updated"),
            @ApiResponse(responseCode = "404", description = "Application not found")
    })

    public ApplicationDTO updateApplication(@PathVariable Integer id, @RequestBody ApplicationDTO applicationDTO) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException(id));

        Opening opening = openingRepository.findById(applicationDTO.getOpeningId())
                .orElseThrow(() -> new RuntimeException("Opening not found with id: " + applicationDTO.getOpeningId()));

        application.setDate(applicationDTO.getDate() != null ? applicationDTO.getDate() : application.getDate());
        application.setFirstName(applicationDTO.getFirstName());
        application.setLastName(applicationDTO.getLastName());
        application.setEmail(applicationDTO.getEmail());
        application.setContactNumber(applicationDTO.getContactNumber());
        application.setDocumentationLink(applicationDTO.getDocumentationLink());
        application.setStatus(applicationDTO.getStatus());
        application.setPoints(applicationDTO.getPoints());
        application.setOpening(opening);

        Application updatedApplication = applicationRepository.save(application);

        return new ApplicationDTO(
                updatedApplication.getId(),
                updatedApplication.getDate(),
                updatedApplication.getFirstName(),
                updatedApplication.getLastName(),
                updatedApplication.getEmail(),
                updatedApplication.getContactNumber(),
                updatedApplication.getDocumentationLink(),
                updatedApplication.getStatus(),
                updatedApplication.getPoints(),
                updatedApplication.getOpening().getId()
        );
    }

    @DeleteMapping("/application/{id}")
    @Operation(summary = "Delete an application", description = "Deletes a job application by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Application deleted"),
            @ApiResponse(responseCode = "404", description = "Application not found")
    })
    public ResponseEntity<String> deleteApplication(@PathVariable Integer id) {
        if (!applicationRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Application with ID " + id + " not found.");
        }

        applicationRepository.deleteById(id);
        return ResponseEntity.ok("Application with ID " + id + " has been deleted successfully.");
    }

}