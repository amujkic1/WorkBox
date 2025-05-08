package com.example.demo.controller;

import com.example.demo.controller.assembler.EmployeeBenefitModelAssembler;
import com.example.demo.dto.EmployeeBenefitDTO;
import com.example.demo.exception.CheckInRecordNotFoundException;
import com.example.demo.exception.EmployeeBenefitNotFoundException;
import com.example.demo.models.CheckInRecord;
import com.example.demo.models.EmployeeBenefit;
import com.example.demo.repository.EmployeeBenefitRepository;
import com.example.demo.service.EmployeeBenefitService;
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
public class EmployeeBenefitController {
    private final EmployeeBenefitRepository employeeBenefitRepository;
    private final EmployeeBenefitModelAssembler employeeBenefitModelAssembler;
    private final EmployeeBenefitService employeeBenefitService;

    public EmployeeBenefitController(EmployeeBenefitRepository employeeBenefitRepository, EmployeeBenefitModelAssembler employeeBenefitModelAssembler, EmployeeBenefitService employeeBenefitService) {
        this.employeeBenefitRepository = employeeBenefitRepository;
        this.employeeBenefitModelAssembler = employeeBenefitModelAssembler;
        this.employeeBenefitService = employeeBenefitService;
    }


    @GetMapping("/employee_benefits")
    public CollectionModel<EntityModel<EmployeeBenefit>> all() {
        List<EntityModel<EmployeeBenefit>> employeeBenefits = employeeBenefitRepository.findAll().stream()
                .map(employeeBenefitModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(employeeBenefits, linkTo(methodOn(EmployeeBenefitController.class).all()).withSelfRel());
    }

    @GetMapping("/employee_benefit/{id}")
    public EntityModel<EmployeeBenefit> one(@PathVariable Integer id){
        EmployeeBenefit employeeBenefit = employeeBenefitRepository.findById(id).orElseThrow(() -> new EmployeeBenefitNotFoundException(id));
        return employeeBenefitModelAssembler.toModel(employeeBenefit);
    }

    @PostMapping("/employee_benefit")
    public ResponseEntity<?> newEmployeeBenefit(@RequestBody EmployeeBenefit newEmployeeBenefit){
        EntityModel<EmployeeBenefit> entityModel = employeeBenefitModelAssembler.toModel(employeeBenefitRepository.save(newEmployeeBenefit));

        return ResponseEntity.
                created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).
                body(entityModel);
    }

    @PutMapping("/employee_benefit/{id}")
    public ResponseEntity<?> replaceEmployeeBenefit(@RequestBody EmployeeBenefit newEmployeeBenefit, @PathVariable Integer id){
        EmployeeBenefit updatedEmployeeBenefit = employeeBenefitRepository.findById(id)
                .map(employeeBenefit -> {
                    employeeBenefit.setType(newEmployeeBenefit.getType());
                    employeeBenefit.setStatus(newEmployeeBenefit.getStatus());
                    employeeBenefit.setDetails(newEmployeeBenefit.getDetails());
                    employeeBenefit.setUser(newEmployeeBenefit.getUser());

                    return employeeBenefitRepository.save(employeeBenefit);
                })
                .orElseGet(() -> {
                    return employeeBenefitRepository.save(newEmployeeBenefit);
                });

        EntityModel<EmployeeBenefit> entityModel = employeeBenefitModelAssembler.toModel(updatedEmployeeBenefit);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/employee_benefit/{id}")
    public ResponseEntity<?> deleteEmployeeBenefit(@PathVariable Integer id){
        employeeBenefitRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/employee_benefit/user/{userId}")
    public List<EmployeeBenefitDTO> getEmployeeBenefitsByUserId(@PathVariable Integer userId) {
        return employeeBenefitRepository.findByUser_UserId(userId)
                .stream()
                .map(benefit -> new EmployeeBenefitDTO(
                        benefit.getEmployeeBenefitId(),
                        benefit.getType(),
                        benefit.getStatus(),
                        benefit.getDetails(),
                        benefit.getUser().getUserId()
                ))
                .collect(Collectors.toList());
    }



    /*
    @PostMapping("/employee_benefits")
    public CollectionModel<EntityModel<EmployeeBenefit>> insertEmployeeBenefits(@RequestBody List<EmployeeBenefit> employeeBenefits) {
        List<EmployeeBenefit> addedEmployeeBenefits = employeeBenefitService.insertEmployeeBenefits(employeeBenefits);
        List<EntityModel<EmployeeBenefit>> insertedEmployeeBenefits = addedEmployeeBenefits.stream()
                .map(employeeBenefitModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(insertedEmployeeBenefits, linkTo(methodOn(EmployeeBenefitController.class).all()).withSelfRel());
    }

    */


}
