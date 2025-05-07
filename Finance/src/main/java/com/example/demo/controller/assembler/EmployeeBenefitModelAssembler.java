package com.example.demo.controller.assembler;

import com.example.demo.controller.EmployeeBenefitController;
import com.example.demo.models.EmployeeBenefit;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EmployeeBenefitModelAssembler implements RepresentationModelAssembler<EmployeeBenefit, EntityModel<EmployeeBenefit>> {

    @Override
    public EntityModel<EmployeeBenefit> toModel(EmployeeBenefit employeeBenefit) {
        return EntityModel.of(employeeBenefit,
                linkTo(methodOn(EmployeeBenefitController.class).one(employeeBenefit.getEmployeeBenefitId())).withSelfRel(),
                linkTo(methodOn(EmployeeBenefitController.class).all()).withRel("employee_benefit"));
    }

}
