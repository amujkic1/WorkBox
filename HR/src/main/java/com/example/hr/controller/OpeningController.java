package com.example.hr.controller;

import com.example.hr.dto.OpeningDTO;
import com.example.hr.exception.ApplicationNotFoundException;
import com.example.hr.exception.OpeningNotFoundException;
import com.example.hr.exception.UserNotFoundException;
import com.example.hr.model.Opening;
import com.example.hr.model.User;
import com.example.hr.repository.OpeningRepository;
import com.example.hr.repository.UserRepository;
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
@Tag(name="OpeningController", description = "API for openings management")
public class OpeningController {

    private final OpeningRepository openingRepository;
    private final UserRepository userRepository;

    public OpeningController(OpeningRepository openingRepository, UserRepository userRepository) {
        this.openingRepository = openingRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/openings")
    @Operation(summary = "Retrieve all openings", description = "Returns a list of all job openings")
    List<OpeningDTO> getAllOpenings(){
        return openingRepository.findAll().stream()
                .map(opening -> new OpeningDTO(
                        opening.getId(),
                        opening.getOpeningName(),
                        opening.getDescription(),
                        opening.getConditions(),
                        opening.getBenefits(),
                        opening.getStartDate(),
                        opening.getEndDate(),
                        opening.getResult(),
                        opening.getUser().getId()
                )).collect(Collectors.toList());
    }

    @GetMapping("/opening/{id}")
    @Operation(summary = "Retrieve an opening by ID", description = "Fetches details of a specific opening by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Opening found"),
            @ApiResponse(responseCode = "404", description = "Opening not found")
    })
    @ExceptionHandler(OpeningNotFoundException.class)
    OpeningDTO getOpeningById(@PathVariable Integer id){
        Opening opening = openingRepository.findById(id)
                .orElseThrow(() -> new OpeningNotFoundException(id));

        return new OpeningDTO(
                opening.getId(),
                opening.getOpeningName(),
                opening.getDescription(),
                opening.getConditions(),
                opening.getBenefits(),
                opening.getStartDate(),
                opening.getEndDate(),
                opening.getResult(),
                opening.getUser().getId()
        );
    }

    @PostMapping("/opening")
    @Operation(summary = "Create a new opening", description = "Adds a new opening to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Opening created"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    OpeningDTO createOpening(@RequestBody OpeningDTO openingDTO) {
        User user = userRepository.findById(openingDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException(openingDTO.getUserId()));

        Opening newOpening = new Opening(
                openingDTO.getOpeningName(),
                openingDTO.getDescription(),
                openingDTO.getConditions(),
                openingDTO.getBenefits(),
                openingDTO.getStartDate(),
                openingDTO.getEndDate(),
                openingDTO.getResult(),
                user
        );

        Opening savedOpening = openingRepository.save(newOpening);

        return openingDTO;
    }

    @PutMapping("/opening/{id}")
    @Operation(summary = "Update an opening", description = "Updates an existing job opening by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Opening updated"),
            @ApiResponse(responseCode = "404", description = "Opening not found")
    })
    public OpeningDTO updateOpening(@PathVariable Integer id, @RequestBody OpeningDTO openingDTO) {
        Opening opening = openingRepository.findById(id)
                .orElseThrow(() -> new OpeningNotFoundException(id));

        User user = userRepository.findById(openingDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException(openingDTO.getUserId()));

        opening.setOpeningName(openingDTO.getOpeningName() != null ? openingDTO.getOpeningName() : opening.getOpeningName());
        opening.setDescription(openingDTO.getDescription() != null ? openingDTO.getDescription() : opening.getDescription());
        opening.setConditions(openingDTO.getConditions() != null ? openingDTO.getConditions() : opening.getConditions());
        opening.setBenefits(openingDTO.getBenefits() != null ? openingDTO.getBenefits() : opening.getBenefits());
        opening.setStartDate(openingDTO.getStartDate() != null ? openingDTO.getStartDate() : opening.getStartDate());
        opening.setEndDate(openingDTO.getEndDate() != null ? openingDTO.getEndDate() : opening.getEndDate());
        opening.setResult(openingDTO.getResult() != null ? openingDTO.getResult() : opening.getResult());
        opening.setUser(user);

        Opening updatedOpening = openingRepository.save(opening);

        return new OpeningDTO(
                updatedOpening.getId(),
                updatedOpening.getOpeningName(),
                updatedOpening.getDescription(),
                updatedOpening.getConditions(),
                updatedOpening.getBenefits(),
                updatedOpening.getStartDate(),
                updatedOpening.getEndDate(),
                updatedOpening.getResult(),
                updatedOpening.getUser().getId()
        );
    }

    @DeleteMapping("/opening/{id}")
    @Operation(summary = "Delete an opening", description = "Deletes a job opening by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Opening deleted"),
            @ApiResponse(responseCode = "404", description = "Opening not found")
    })
    public ResponseEntity<String> deleteOpening(@PathVariable Integer id) {
        if (!openingRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Opening with ID " + id + " not found.");
        }

        openingRepository.deleteById(id);
        return ResponseEntity.ok("Opening with ID " + id + " has been deleted successfully.");
    }

}