package com.example.hr.controller;

import com.example.hr.dto.RecordDTO;
import com.example.hr.exception.RecordNotFoundException;
import com.example.hr.exception.RequestNotFoundException;
import com.example.hr.model.Record;
import com.example.hr.repository.RecordRepository;
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
@Tag(name="RecordController", description = "API for records management")
public class RecordController {

    private final RecordRepository recordRepository;

    public RecordController(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    @GetMapping("/records")
    @Operation(summary = "Retrieve all records", description = "Returns a list of all employee records")
    List<RecordDTO> getAllRecords(){
        return recordRepository.findAll().stream()
                .map(record -> new RecordDTO(
                        record.getId(),
                        record.getEmploymentDate(),
                        record.getStatus(),
                        record.getWorkingHours()
                )).collect(Collectors.toList());
    }

    @GetMapping("/record/{id}")
    @Operation(summary = "Retrieve a record by ID", description = "Fetches details of a specific record by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Record found"),
            @ApiResponse(responseCode = "404", description = "Record not found")
    })
    @ExceptionHandler(RecordNotFoundException.class)
    RecordDTO getRecordById(@PathVariable Integer id){
        Record record = recordRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id));
        return new RecordDTO(
                record.getId(),
                record.getEmploymentDate(),
                record.getStatus(),
                record.getWorkingHours()
        );
    }

    @PostMapping("/record")
    @Operation(summary = "Create a new record", description = "Adds a new employee record to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Record created"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    Record postRecord(@RequestBody Record record){
        return recordRepository.save(record);
    }

    @PutMapping("/record/{id}")
    @Operation(summary = "Update a record", description = "Updates an existing employee record by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Record updated"),
            @ApiResponse(responseCode = "404", description = "Record not found")
    })
    public Record updateRecord(@PathVariable Integer id, @RequestBody Record updatedRecord) {
        return recordRepository.findById(id).map(record -> {

            record.setJMBG(updatedRecord.getJMBG());
            record.setBirthDate(updatedRecord.getBirthDate());
            record.setContactNumber(updatedRecord.getContactNumber());
            record.setAddress(updatedRecord.getAddress());
            record.setEmail(updatedRecord.getEmail());
            record.setEmploymentDate(updatedRecord.getEmploymentDate());
            record.setStatus(updatedRecord.getStatus());
            record.setWorkingHours(updatedRecord.getWorkingHours());

            return recordRepository.save(record);
        }).orElseThrow(() -> new RecordNotFoundException(id));
    }

    @DeleteMapping("record/{id}")
    @Operation(summary = "Delete a record", description = "Deletes an employee record by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request deleted"),
            @ApiResponse(responseCode = "404", description = "Request not found")
    })
    public ResponseEntity<String> deleteRecord(@PathVariable Integer id){
        if(!recordRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Record with id " + id + " not found");
        }

        recordRepository.deleteById(id);
        return ResponseEntity.ok("Record with id " + id + "deleted successfully");
    }

}