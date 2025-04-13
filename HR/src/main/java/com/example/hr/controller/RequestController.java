package com.example.hr.controller;

import com.example.hr.assembler.RequestModelAssembler;
import com.example.hr.dto.RequestDTO;
import com.example.hr.exception.RecordNotFoundException;
import com.example.hr.exception.RequestNotFoundException;
import com.example.hr.exception.UserNotFoundException;
import com.example.hr.model.Record;
import com.example.hr.model.Request;
import com.example.hr.model.User;
import com.example.hr.repository.RecordRepository;
import com.example.hr.repository.RequestRepository;
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
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@Tag(name="RequestController", description = "API for requests management")
public class RequestController {

    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RequestModelAssembler requestModelAssembler;

    public RequestController(RequestRepository requestRepository, UserRepository userRepository,
                             ModelMapper modelMapper, RequestModelAssembler requestModelAssembler) {
        this.requestRepository = requestRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.requestModelAssembler = requestModelAssembler;
    }

    @GetMapping("/requests")
    @Operation(summary = "Retrieve all requests", description = "Returns a list of all employee requests")
    public CollectionModel<EntityModel<RequestDTO>> all() {
        List<EntityModel<RequestDTO>> requests = requestRepository.findAll().stream()
                .map(request -> requestModelAssembler.toModel(modelMapper.map(request, RequestDTO.class)))
                .collect(Collectors.toList());
        return CollectionModel.of(requests, linkTo(methodOn(RequestController.class).all()).withSelfRel());
    }

    @GetMapping("/requests/{id}")
    @Operation(summary = "Retrieve a request by ID", description = "Fetches details of a specific request by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request found"),
            @ApiResponse(responseCode = "404", description = "Request not found")
    })
    @ExceptionHandler(RequestNotFoundException.class)
    public EntityModel<RequestDTO> one(@PathVariable Integer id) {
        Request request = requestRepository.findById(id).orElseThrow(() -> new RequestNotFoundException(id));
        RequestDTO requestDTO = modelMapper.map(request, RequestDTO.class);
        return requestModelAssembler.toModel(requestDTO);
    }

    @PostMapping("/requests")
    @Operation(summary = "Create a new request", description = "Adds a new employee request to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Request created"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<?> newRequest(@Valid  @RequestBody RequestDTO requestDTO){

        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException(requestDTO.getUserId()));

        Request request = modelMapper.map(requestDTO, Request.class);
        request.setUser(user);
        Request savedRequest = requestRepository.save(request);
        RequestDTO savedRequestDTO = modelMapper.map(savedRequest, RequestDTO.class);

        EntityModel<RequestDTO> entityModel = requestModelAssembler.toModel(savedRequestDTO);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PutMapping("/requests/{id}")
    @Operation(summary = "Update a request", description = "Updates an existing employee request by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request updated"),
            @ApiResponse(responseCode = "404", description = "Request not found")
    })
    public ResponseEntity<?> updateRequest(@Valid @RequestBody RequestDTO requestDTO, @PathVariable Integer id) {
        Request updatedRequest = requestRepository.findById(id)
                .map(request -> {
                    if (requestDTO.getType() != null) {
                        request.setType(requestDTO.getType());
                    }
                    if (requestDTO.getText() != null) {
                        request.setText(requestDTO.getText());
                    }
                    if (requestDTO.getDate() != null) {
                        request.setDate(requestDTO.getDate());
                    }
                    if (requestDTO.getStatus() != null) {
                        request.setStatus(requestDTO.getStatus());
                    }
                    if (requestDTO.getUserId() != null) {
                        User user = userRepository.findById(requestDTO.getUserId())
                                .orElseThrow(() -> new UserNotFoundException(requestDTO.getUserId()));
                        request.setUser(user);
                    }

                    return requestRepository.save(request);
                })
                .orElseThrow(() -> new RequestNotFoundException(id));

        RequestDTO updatedRequestDTO = modelMapper.map(updatedRequest, RequestDTO.class);
        EntityModel<RequestDTO> entityModel = requestModelAssembler.toModel(updatedRequestDTO);
        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/requests/{id}")
    @Operation(summary = "Delete a request", description = "Deletes an employee request by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request deleted"),
            @ApiResponse(responseCode = "404", description = "Request not found")
    })
    public ResponseEntity<?> deleteRequest(@PathVariable Integer id) {
        Request request = requestRepository.findById(id)
                .orElseThrow(() -> new RequestNotFoundException(id));

        requestRepository.delete(request);

        return ResponseEntity.ok().body(Collections.singletonMap("message", "Request successfully deleted."));
    }

}