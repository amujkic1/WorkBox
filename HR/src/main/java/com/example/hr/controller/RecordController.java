package com.example.hr.controller;

import com.example.hr.dto.RecordDTO;
import com.example.hr.exception.RecordNotFoundException;
import com.example.hr.model.Record;
import com.example.hr.repository.RecordRepository;
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
@Tag(name="RecordController", description = "API for records management")
public class RecordController {

    private final RecordRepository recordRepository;
    private final ModelMapper modelMapper;

    public RecordController(RecordRepository recordRepository, ModelMapper modelMapper) {
        this.recordRepository = recordRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/records")
    @Operation(summary = "Retrieve all records", description = "Returns a list of all employee records")
    List<RecordDTO> getAllRecords() {
        return recordRepository.findAll().stream()
                .map(record -> modelMapper.map(record, RecordDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/records/{id}")
    @Operation(summary = "Retrieve a record by ID", description = "Fetches details of a specific record by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Record found"),
            @ApiResponse(responseCode = "404", description = "Record not found")
    })
    @ExceptionHandler(RecordNotFoundException.class)
    RecordDTO getRecordById(@PathVariable Integer id) {
        Record record = recordRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id));
        return modelMapper.map(record, RecordDTO.class);
    }

    @PostMapping("/records")
    @Operation(summary = "Create a new record", description = "Adds a new employee record to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Record created"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    RecordDTO postRecord(@RequestBody RecordDTO recordDTO) {
        Record record = modelMapper.map(recordDTO, Record.class);
        Record savedRecord = recordRepository.save(record);
        return modelMapper.map(savedRecord, RecordDTO.class);
    }

    @PutMapping("/records/{id}")
    @Operation(summary = "Update a record", description = "Updates an existing employee record by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Record updated"),
            @ApiResponse(responseCode = "404", description = "Record not found")
    })
    public RecordDTO updateRecord(@PathVariable Integer id, @RequestBody RecordDTO recordDTO) {
        Record record = recordRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id));

        modelMapper.map(recordDTO, record);

        Record updatedRecord = recordRepository.save(record);
        return modelMapper.map(updatedRecord, RecordDTO.class);
    }

    @DeleteMapping("records/{id}")
    @Operation(summary = "Delete a record", description = "Deletes an employee record by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request deleted"),
            @ApiResponse(responseCode = "404", description = "Request not found")
    })
    public ResponseEntity<String> deleteRecord(@PathVariable Integer id) {
        if (!recordRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Record with id " + id + " not found");
        }

        recordRepository.deleteById(id);
        return ResponseEntity.ok("Record with id " + id + " deleted successfully");
    }

}