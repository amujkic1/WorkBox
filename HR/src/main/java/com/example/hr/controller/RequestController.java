package com.example.hr.controller;

import com.example.hr.dto.RequestDTO;
import com.example.hr.exception.ApplicationNotFoundException;
import com.example.hr.exception.RecordNotFoundException;
import com.example.hr.exception.RequestNotFoundException;
import com.example.hr.model.Record;
import com.example.hr.model.Request;
import com.example.hr.repository.RecordRepository;
import com.example.hr.repository.RequestRepository;
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
@Tag(name="RequestController", description = "API for requests management")
public class RequestController {

    private final RequestRepository requestRepository;
    private final RecordRepository recordRepository;

    public RequestController(RequestRepository requestRepository, RecordRepository recordRepository) {
        this.requestRepository = requestRepository;
        this.recordRepository = recordRepository;
    }

    @GetMapping("/requests")
    @Operation(summary = "Retrieve all requests", description = "Returns a list of all employee requests")
    List<RequestDTO> getAllRequests(){
        return requestRepository.findAll().stream()
                .map(request -> new RequestDTO(
                        request.getId(),
                        request.getType(),
                        request.getText(),
                        request.getDate(),
                        request.getStatus(),
                        request.getRecord().getId()
                )).collect(Collectors.toList());
    }

    @GetMapping("/request/{id}")
    @Operation(summary = "Retrieve a request by ID", description = "Fetches details of a specific request by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request found"),
            @ApiResponse(responseCode = "404", description = "Request not found")
    })
    @ExceptionHandler(RequestNotFoundException.class)
    RequestDTO getRequestById(@PathVariable Integer id){
        Request request = requestRepository.findById(id)
                .orElseThrow(() -> new RequestNotFoundException(id));

        return new RequestDTO(
                request.getId(),
                request.getType(),
                request.getText(),
                request.getDate(),
                request.getStatus(),
                request.getRecord().getId()
        );
    }

    @PostMapping("/request")
    @Operation(summary = "Create a new request", description = "Adds a new employee request to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Request created"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    RequestDTO postRequest(@RequestBody RequestDTO requestDTO){
        Record record = recordRepository.findById(requestDTO.getRecordId())
                .orElseThrow(() -> new RecordNotFoundException(requestDTO.getRecordId()));

        Request request = new Request(
                requestDTO.getType(),
                requestDTO.getText(),
                requestDTO.getDate(),
                requestDTO.getStatus(),
                record
        );

        Request savedRequest = requestRepository.save(request);

        return requestDTO;

    }

    @PutMapping("/request/{id}")
    @Operation(summary = "Update a request", description = "Updates an existing employee request by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request updated"),
            @ApiResponse(responseCode = "404", description = "Request not found")
    })
    RequestDTO updateRequest(@PathVariable Integer id, @RequestBody RequestDTO requestDTO) {
        Request request = requestRepository.findById(id)
                .orElseThrow(() -> new RequestNotFoundException(id));

        request.setType(requestDTO.getType());
        request.setText(requestDTO.getText());
        request.setDate(requestDTO.getDate());
        request.setStatus(requestDTO.getStatus());

        Record record = recordRepository.findById(requestDTO.getRecordId())
                .orElseThrow(() -> new RecordNotFoundException(requestDTO.getRecordId()));
        request.setRecord(record);

        Request updatedRequest = requestRepository.save(request);

        return new RequestDTO(
                updatedRequest.getId(),
                updatedRequest.getType(),
                updatedRequest.getText(),
                updatedRequest.getDate(),
                updatedRequest.getStatus(),
                updatedRequest.getRecord().getId()
        );
    }

    @DeleteMapping("/request/{id}")
    @Operation(summary = "Delete a request", description = "Deletes an employee request by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request deleted"),
            @ApiResponse(responseCode = "404", description = "Request not found")
    })
    public ResponseEntity<String> deleteRequest(@PathVariable Integer id){
        if(!requestRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Request with id " + id + " not found");
        }

        requestRepository.deleteById(id);
        return ResponseEntity.ok("Request with id " + id + "deleted successfully");
    }

}