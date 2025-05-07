package com.example.demo.controller.assembler;

import com.example.demo.controller.CheckInRecordController;
import com.example.demo.models.CheckInRecord;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CheckInRecordModelAssembler implements RepresentationModelAssembler<CheckInRecord, EntityModel<CheckInRecord>> {
    @Override
    public EntityModel<CheckInRecord> toModel(CheckInRecord checkInRecord) {
        return EntityModel.of(checkInRecord,
                linkTo(methodOn(CheckInRecordController.class).one(checkInRecord.getCheckInRecordId())).withSelfRel(),
                linkTo(methodOn(CheckInRecordController.class).all()).withRel("check_in_records"));
    }
}
