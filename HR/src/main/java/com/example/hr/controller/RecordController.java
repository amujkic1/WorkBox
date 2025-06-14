package com.example.hr.controller;

import com.example.hr.assembler.RecordModelAssembler;
import com.example.hr.dto.RecordDTO;
import com.example.hr.exception.RecordNotFoundException;
import com.example.hr.model.Record;
import com.example.hr.model.User;
import com.example.hr.repository.RecordRepository;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/hr")
@Tag(name="RecordController", description = "API for records management")
public class RecordController {

    private final RecordRepository recordRepository;
    private final ModelMapper modelMapper;
    private final RecordModelAssembler recordModelAssembler;
    private final UserRepository userRepository;

    public RecordController(RecordRepository recordRepository, ModelMapper modelMapper,
                            RecordModelAssembler recordModelAssembler, UserRepository userRepository) {
        this.recordRepository = recordRepository;
        this.modelMapper = modelMapper;
        this.recordModelAssembler = recordModelAssembler;
        this.userRepository = userRepository;
    }

    @GetMapping("/records")
    @Operation(summary = "Retrieve all records", description = "Returns a list of all employee records")
    public CollectionModel<EntityModel<RecordDTO>> all() {
        List<EntityModel<RecordDTO>> records = recordRepository.findAll().stream()
                .map(record -> recordModelAssembler.toModel(modelMapper.map(record, RecordDTO.class)))
                .collect(Collectors.toList());
        return CollectionModel.of(records, linkTo(methodOn(RecordController.class).all()).withSelfRel());
    }

    @GetMapping("/records/{id}")
    @Operation(summary = "Retrieve a record by ID", description = "Fetches details of a specific record by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Record found"),
            @ApiResponse(responseCode = "404", description = "Record not found")
    })
    public EntityModel<RecordDTO> one(@PathVariable Integer id) {
        Record record = recordRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id));
        RecordDTO recordDTO = modelMapper.map(record, RecordDTO.class);
        return recordModelAssembler.toModel(recordDTO);
    }

    @PostMapping("/records")
    public ResponseEntity<?> postRecord(@Valid @RequestBody Record record) {
        Record savedRecord = recordRepository.save(record);

        RecordDTO responseDTO = modelMapper.map(savedRecord, RecordDTO.class);

        EntityModel<RecordDTO> entityModel = recordModelAssembler.toModel(responseDTO);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/records/{id}")
    @Operation(summary = "Update a record", description = "Updates an existing employee record by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Record updated"),
            @ApiResponse(responseCode = "404", description = "Record not found")
    })
    public ResponseEntity<?> updateRecord(@Valid @PathVariable Integer id, @RequestBody Record record) {
        Record updatedRecord = recordRepository.findById(id)
                .map(_record -> {
                    if(record.getJmbg() != null){
                        record.setJmbg(record.getJmbg());
                    }
                    if(record.getBirthDate() != null){
                        record.setJmbg(record.getJmbg());
                    }
                    if(record.getContactNumber() != null){
                        record.setContactNumber(record.getContactNumber());
                    }
                    if(record.getAddress() != null){
                        record.setAddress(record.getAddress());
                    }
                    if(record.getEmail() != null){
                        record.setEmail(record.getEmail());
                    }
                    if(record.getEmploymentDate() != null){
                        record.setEmploymentDate(record.getEmploymentDate());
                    }
                    if (record.getStatus() != null) {
                        record.setStatus(record.getStatus());
                    }
                    if(record.getWorkingHours() != null) {
                        record.setWorkingHours(record.getWorkingHours());
                    }
                    return recordRepository.save(record);
                })
                .orElseThrow(() -> new RecordNotFoundException(id));

        RecordDTO updatedRecordDTO = modelMapper.map(updatedRecord, RecordDTO.class);
        EntityModel<RecordDTO> entityModel = recordModelAssembler.toModel(updatedRecordDTO);
        return ResponseEntity.ok(entityModel);
    }

/*    @Transactional
    @DeleteMapping("records/{id}")
    @Operation(summary = "Delete a record", description = "Deletes an employee record by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Record deleted"),
            @ApiResponse(responseCode = "404", description = "Record not found")
    })
    public ResponseEntity<?> deleteRecord(@PathVariable Integer id) {
        Record record = recordRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id));

        // Prvo obriši sve korisnike povezane sa Record-om
        List<User> users = userRepository.findAllByRecord(record);
        userRepository.deleteAll(users);

        // Sada obriši i Record
        recordRepository.delete(record);

        return ResponseEntity.ok().body(Collections.singletonMap("message", "Record successfully deleted."));
    }*/

    @DeleteMapping("records/{id}")
    @Operation(summary = "Delete a record", description = "Deletes an employee record by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request deleted"),
            @ApiResponse(responseCode = "404", description = "Request not found")
    })
    public ResponseEntity<?> deleteRecord(@PathVariable Integer id) {
        Record record = recordRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id));

        recordRepository.delete(record);

        return ResponseEntity.ok().body(Collections.singletonMap("message", "Record successfully deleted."));
    }

}