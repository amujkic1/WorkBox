package com.example.hr.assembler;

import com.example.hr.controller.ApplicationController;
import com.example.hr.dto.ApplicationDTO;
import com.example.hr.dto.UserDTO;
import com.example.hr.model.Application;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<UserDTO, EntityModel<UserDTO>> {

    @Override
    public EntityModel<UserDTO> toModel(UserDTO user) {

        return EntityModel.of(user,
                linkTo(methodOn(ApplicationController.class).one(user.getId())).withSelfRel(),
                linkTo(methodOn(ApplicationController.class).all()).withRel("applications"));
    }
}