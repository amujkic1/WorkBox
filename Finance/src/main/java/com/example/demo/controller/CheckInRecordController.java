package com.example.demo.controller;

import com.example.demo.controller.assembler.CheckInRecordModelAssembler;
import com.example.demo.exception.CheckInRecordNotFoundException;
import com.example.demo.models.CheckInRecord;
import com.example.demo.repository.CheckInRecordRepository;
import com.example.demo.repository.UserRepository;
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
public class CheckInRecordController {
    private final CheckInRecordRepository checkInRecordRepository;
    private final CheckInRecordModelAssembler checkInRecordModelAssembler;
    private final UserRepository userRepository;

    public CheckInRecordController(CheckInRecordRepository checkInRecordRepository, CheckInRecordModelAssembler checkInRecordModelAssembler, UserRepository userRepository) {
        this.checkInRecordRepository = checkInRecordRepository;
        this.checkInRecordModelAssembler = checkInRecordModelAssembler;
        this.userRepository = userRepository;
    }


    @GetMapping("/check_in_records")
    public CollectionModel<EntityModel<CheckInRecord>> all(){
        List<EntityModel<CheckInRecord>> checkInRecords = checkInRecordRepository.findAll().stream()
                .map(checkInRecordModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(checkInRecords, linkTo(methodOn(CheckInRecordController.class).all()).withSelfRel());
    }

    @GetMapping("/check_in_record/{id}")
    public EntityModel<CheckInRecord> one(@PathVariable Integer id){
        CheckInRecord checkInRecord = checkInRecordRepository.findById(id).orElseThrow(() -> new CheckInRecordNotFoundException(id));
        return checkInRecordModelAssembler.toModel(checkInRecord);
    }

    @PostMapping("/check_in_record")
    public ResponseEntity<?> newCheckInRecord(@RequestBody CheckInRecord newcheckInRecord){
        EntityModel<CheckInRecord> entityModel = checkInRecordModelAssembler.toModel(checkInRecordRepository.save(newcheckInRecord));

        return ResponseEntity.
                created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).
                body(entityModel);
    }

    @PutMapping("/check_in_record/{id}")
    public ResponseEntity<?> replaceCheckInRecord(@RequestBody CheckInRecord newcheckInRecord, @PathVariable Integer id){
        CheckInRecord updatedCheckInRecord = checkInRecordRepository.findById(id)
                .map(checkInRecord -> {
                    checkInRecord.setCheckInDate(newcheckInRecord.getCheckInDate());
                    checkInRecord.setCheckInTime(newcheckInRecord.getCheckInTime());
                    checkInRecord.setCheckOutTime(newcheckInRecord.getCheckOutTime());
                    checkInRecord.setUser(newcheckInRecord.getUser());
                    return checkInRecordRepository.save(checkInRecord);
                })
                .orElseGet(() -> {
                    return checkInRecordRepository.save(newcheckInRecord);
                });

        EntityModel<CheckInRecord> entityModel = checkInRecordModelAssembler.toModel(updatedCheckInRecord);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/check_in_record/{id}")
    public ResponseEntity<?> deleteCheckInRecord(@PathVariable Integer id){
        checkInRecordRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
