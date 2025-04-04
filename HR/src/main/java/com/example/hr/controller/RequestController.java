package com.example.hr.controller;

import com.example.hr.dto.RequestDTO;
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
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

    public RequestController(RequestRepository requestRepository, RecordRepository recordRepository, ModelMapper modelMapper) {
        this.requestRepository = requestRepository;
        this.recordRepository = recordRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/requests")
    @Operation(summary = "Retrieve all requests", description = "Returns a list of all employee requests")
    List<RequestDTO> getAllRequests() {
        return requestRepository.findAll().stream()
                .map(request -> modelMapper.map(request, RequestDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/requests/{id}")
    @Operation(summary = "Retrieve a request by ID", description = "Fetches details of a specific request by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request found"),
            @ApiResponse(responseCode = "404", description = "Request not found")
    })
    @ExceptionHandler(RequestNotFoundException.class)
    RequestDTO getRequestById(@PathVariable Integer id) {
        Request request = requestRepository.findById(id)
                .orElseThrow(() -> new RequestNotFoundException(id));
        return modelMapper.map(request, RequestDTO.class);
    }

    @PostMapping("/requests")
    @Operation(summary = "Create a new request", description = "Adds a new employee request to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Request created"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    RequestDTO postRequest(@RequestBody RequestDTO requestDTO) {
        Record record = recordRepository.findById(requestDTO.getRecordId())
                .orElseThrow(() -> new RecordNotFoundException(requestDTO.getRecordId()));

        Request request = modelMapper.map(requestDTO, Request.class);
        request.setRecord(record);

        Request savedRequest = requestRepository.save(request);

        return modelMapper.map(savedRequest, RequestDTO.class);
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

        modelMapper.map(requestDTO, request);

        Record record = recordRepository.findById(requestDTO.getRecordId())
                .orElseThrow(() -> new RecordNotFoundException(requestDTO.getRecordId()));
        request.setRecord(record);

        Request updatedRequest = requestRepository.save(request);
        return modelMapper.map(updatedRequest, RequestDTO.class);
    }

    @DeleteMapping("/requests/{id}")
    @Operation(summary = "Delete a request", description = "Deletes an employee request by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request deleted"),
            @ApiResponse(responseCode = "404", description = "Request not found")
    })
    public ResponseEntity<String> deleteRequest(@PathVariable Integer id) {
        if (!requestRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Request with id " + id + " not found");
        }

        requestRepository.deleteById(id);
        return ResponseEntity.ok("Request with id " + id + " deleted successfully");
    }

}