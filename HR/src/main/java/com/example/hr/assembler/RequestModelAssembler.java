package com.example.hr.assembler;

import com.example.hr.controller.ApplicationController;
import com.example.hr.dto.ApplicationDTO;
import com.example.hr.dto.RequestDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RequestModelAssembler implements RepresentationModelAssembler<RequestDTO, EntityModel<RequestDTO>> {

    @Override
    public EntityModel<RequestDTO> toModel(RequestDTO request) {

        return EntityModel.of(request,
                linkTo(methodOn(ApplicationController.class).one(request.getId())).withSelfRel(),
                linkTo(methodOn(ApplicationController.class).all()).withRel("applications"));
    }
}