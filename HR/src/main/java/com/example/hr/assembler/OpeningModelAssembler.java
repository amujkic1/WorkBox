package com.example.hr.assembler;

import com.example.hr.controller.ApplicationController;
import com.example.hr.dto.ApplicationDTO;
import com.example.hr.dto.OpeningDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OpeningModelAssembler implements RepresentationModelAssembler<OpeningDTO, EntityModel<OpeningDTO>> {

    @Override
    public EntityModel<OpeningDTO> toModel(OpeningDTO opening) {

        return EntityModel.of(opening,
                linkTo(methodOn(ApplicationController.class).one(opening.getId())).withSelfRel(),
                linkTo(methodOn(ApplicationController.class).all()).withRel("applications"));
    }
}