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
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

    public OpeningController(OpeningRepository openingRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.openingRepository = openingRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/openings")
    @Operation(summary = "Retrieve all openings", description = "Returns a list of all job openings")
    List<OpeningDTO> getAllOpenings() {
        return openingRepository.findAll().stream()
                .map(opening -> modelMapper.map(opening, OpeningDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/openings/{id}")
    @Operation(summary = "Retrieve an opening by ID", description = "Fetches details of a specific opening by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Opening found"),
            @ApiResponse(responseCode = "404", description = "Opening not found")
    })
    @ExceptionHandler(OpeningNotFoundException.class)
    OpeningDTO getOpeningById(@PathVariable Integer id) {
        Opening opening = openingRepository.findById(id)
                .orElseThrow(() -> new OpeningNotFoundException(id));

        return modelMapper.map(opening, OpeningDTO.class);
    }

    @PostMapping("/openings")
    @Operation(summary = "Create a new opening", description = "Adds a new opening to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Opening created"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    OpeningDTO createOpening(@RequestBody OpeningDTO openingDTO) {
        User user = userRepository.findById(openingDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException(openingDTO.getUserId()));

        Opening newOpening = modelMapper.map(openingDTO, Opening.class);
        newOpening.setUser(user);

        Opening savedOpening = openingRepository.save(newOpening);

        return modelMapper.map(savedOpening, OpeningDTO.class);
    }

    @PutMapping("/openings/{id}")
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

        modelMapper.map(openingDTO, opening);
        opening.setUser(user);

        Opening updatedOpening = openingRepository.save(opening);

        return modelMapper.map(updatedOpening, OpeningDTO.class);
    }

    @DeleteMapping("/openings/{id}")
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