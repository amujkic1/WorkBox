package com.example.hr.assembler;

import com.example.hr.controller.ApplicationController;
import com.example.hr.dto.ApplicationDTO;
import com.example.hr.dto.RecordDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RecordModelAssembler implements RepresentationModelAssembler<RecordDTO, EntityModel<RecordDTO>> {

    @Override
    public EntityModel<RecordDTO> toModel(RecordDTO record) {

        return EntityModel.of(record,
                linkTo(methodOn(ApplicationController.class).one(record.getId())).withSelfRel(),
                linkTo(methodOn(ApplicationController.class).all()).withRel("applications"));
    }
}