package com.example.business.controller;

import com.example.business.controller.assembler.UserModelAssembler;
import com.example.business.exception.UserNotFoundException;
import com.example.business.model.User;
import com.example.business.service.UserService;
import jakarta.validation.Valid;
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
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserModelAssembler userModelAssembler;

    public UserController(UserService userService, UserModelAssembler userModelAssembler) {
        this.userService = userService;
        this.userModelAssembler = userModelAssembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<User>>all() {
        List<EntityModel<User>> users = userService.getAllUsers().stream()
                .map(userModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(users, linkTo(methodOn(TeamController.class).all()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<User> one(@PathVariable Integer id) {
        User user = userService.getUserById(id).orElseThrow(()->new UserNotFoundException(id));
        return userModelAssembler.toModel(user);
    }

    @PutMapping("/{id}")
    ResponseEntity<?>replaceUser(@RequestBody @Valid User newUser, @PathVariable Integer id) {
        User updatedUser = userService.getUserById(id)
                .map(user -> {
                    user.setFirstName(newUser.getFirstName());
                    user.setLastName(newUser.getLastName());
                    user.setTeam(newUser.getTeam());
                    user.setUuid();
                    return userService.saveUser(user);
                })
                .orElseGet(()->{
                    return userService.saveUser(newUser);
                });
        EntityModel<User> entityModel = userModelAssembler.toModel(updatedUser);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?>deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
