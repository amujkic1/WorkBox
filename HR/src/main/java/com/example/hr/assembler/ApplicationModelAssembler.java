package com.example.hr.assembler;

import com.example.hr.controller.ApplicationController;
import com.example.hr.dto.ApplicationDTO;
import com.example.hr.model.Application;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ApplicationModelAssembler implements RepresentationModelAssembler<ApplicationDTO, EntityModel<ApplicationDTO>> {

    @Override
    public EntityModel<ApplicationDTO> toModel(ApplicationDTO application) {

        return EntityModel.of(application,
                linkTo(methodOn(ApplicationController.class).one(application.getId())).withSelfRel(),
                linkTo(methodOn(ApplicationController.class).all()).withRel("applications"));
    }
}