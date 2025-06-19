package com.example.demo.controller;

import com.example.demo.controller.assembler.CheckInRecordModelAssembler;
import com.example.demo.dto.EmployeeStatus;
import com.example.demo.dto.PayrollDTO;
import com.example.demo.dto.RecordDTO;
import com.example.demo.dto.WorkingHours;
import com.example.demo.exception.CheckInRecordNotFoundException;
import com.example.demo.models.CheckInRecord;
import com.example.demo.models.User;
import com.example.demo.repository.CheckInRecordRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CheckInRecordService;
import com.example.demo.service.ReportService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class CheckInRecordController {
    private final CheckInRecordRepository checkInRecordRepository;
    private final CheckInRecordModelAssembler checkInRecordModelAssembler;
    private final CheckInRecordService checkInRecordService;

    private final UserRepository userRepository;

    private final ReportService reportService;


    public CheckInRecordController(CheckInRecordRepository checkInRecordRepository, CheckInRecordModelAssembler checkInRecordModelAssembler, CheckInRecordService checkInRecordService, UserRepository userRepository, ReportService reportService) {
        this.checkInRecordRepository = checkInRecordRepository;
        this.checkInRecordModelAssembler = checkInRecordModelAssembler;
        this.checkInRecordService = checkInRecordService;
        this.userRepository = userRepository;

        this.reportService = reportService;
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
        Optional<User> userOpt = userRepository.findByUserUUID(newcheckInRecord.getUser().getUserUUID());
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        newcheckInRecord.setUser(userOpt.get());
        CheckInRecord savedRecord = checkInRecordRepository.save(newcheckInRecord);
        EntityModel<CheckInRecord> entityModel = checkInRecordModelAssembler.toModel(savedRecord);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);

        /*EntityModel<CheckInRecord> entityModel = checkInRecordModelAssembler.toModel(checkInRecordRepository.save(newcheckInRecord));

        return ResponseEntity.
                created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).
                body(entityModel);

         */
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

    /*
    @PatchMapping(path = "/check_in_record/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<?> updateCheckOutTime(@PathVariable Integer id, @RequestBody JsonPatch patch) {
        try {
            CheckInRecord checkInRecord = checkInRecordRepository.findById(id)
                    .orElseThrow(() -> new CheckInRecordNotFoundException(id));
            CheckInRecord checkInRecordPatched = applyPatchToCheckInRecord(patch, checkInRecord);

            CheckInRecord updatedCheckInRecord = checkInRecordService.updateCheckOutTime(checkInRecordPatched);
            EntityModel<CheckInRecord> entityModel = checkInRecordModelAssembler.toModel(updatedCheckInRecord);

            //return ResponseEntity.ok(entityModel);
            return ResponseEntity
                    .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                    .body(entityModel);
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (CheckInRecordNotFoundException e) {
            throw new RuntimeException(e.getMessage()+" Greška prilikom pronalaska sloga u patch metodi.");
        }
    }

     */

    @PatchMapping(path = "/check_in_record/{uuid}", consumes = "application/json-patch+json")
    public ResponseEntity<?> updateCheckOutTime(@PathVariable String uuid, @RequestBody JsonPatch patch) {
        try {
            System.out.println("\n\n ***** PATCH *****************************************");
            // Pronađi korisnika po UUID-u
            Optional<User> user = userRepository.findByUserUUID(UUID.fromString(uuid));
            System.out.println("User kojem odgovara ova izmjena: "+user.get().getUserUUID()+" "+user.get().getFirstName());

            if (user.isEmpty()) {
                return ResponseEntity.badRequest().body("User not found");
            }


            LocalDate localDate = LocalDate.now();
            Date currentDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            //Optional<CheckInRecord> recordOpt = checkInRecordRepository.findByUserAndCheckInDate(user.get(), currentDate);
            Optional<CheckInRecord> recordOpt = checkInRecordRepository.findTopByUserAndCheckInDateOrderByCheckInTimeDesc(user.get(), currentDate);

            if (recordOpt.isEmpty()) {
                return ResponseEntity.badRequest().body("Check-in record not found for user: " + uuid + " on " + currentDate);
            }
            System.out.println("\n Pronađeni rekord:"+recordOpt.get().getCheckInRecordId());

            CheckInRecord checkInRecord = recordOpt.get();
            CheckInRecord checkInRecordPatched = applyPatchToCheckInRecord(patch, checkInRecord);

            // Ažuriraj zapis
            CheckInRecord updatedCheckInRecord = checkInRecordService.updateCheckOutTime(checkInRecordPatched);
            EntityModel<CheckInRecord> entityModel = checkInRecordModelAssembler.toModel(updatedCheckInRecord);

            System.out.println("##################### KRAJ ##############################\n\n");
            return ResponseEntity
                    .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                    .body(entityModel);

        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error applying patch: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }




    private CheckInRecord applyPatchToCheckInRecord(JsonPatch patch, CheckInRecord checkInRecord) throws JsonPatchException, JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode patched = patch.apply(objectMapper.convertValue(checkInRecord, JsonNode.class));
        return objectMapper.treeToValue(patched, CheckInRecord.class);
    }


    @DeleteMapping("/check_in_record/{id}")
    public ResponseEntity<?> deleteCheckInRecord(@PathVariable Integer id){
        checkInRecordRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/test")
    public List<RecordDTO> getTest(){
        List<RecordDTO> response = reportService.getAllRecords();
        return response;
    }


    @GetMapping("/timesheet_report")
    public List<WorkingHours> getTimesheetReportForAllUsers(
            @RequestParam("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate
    ) {
        return reportService.calculateWorkingHours(fromDate, toDate);
    }


    @GetMapping("/employee_status_report")
    public List<EmployeeStatus> getEmployeeStatusReport(){
        List<EmployeeStatus> response = reportService.generateEmployeeStatusReport();
        return response;
    }


    @GetMapping("/employees_payroll")
    public List<PayrollDTO> getEmployeesPayroll(
            @RequestParam("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate)
    {
        List<PayrollDTO> response = reportService.generateEmployeesPayroll(fromDate, toDate);
        return response;
    }

    @GetMapping("/test3")
    public List<User> getTest3(){
        List<User> response = reportService.getAllUsersFromAuthService();
        return response;
    }





}
