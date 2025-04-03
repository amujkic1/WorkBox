package com.example.demo.controller;

import com.example.demo.controller.assembler.UserModelAssembler;
import com.example.demo.exception.CheckInRecordNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.models.CheckInRecord;
import com.example.demo.models.User;
import com.example.demo.repository.UserRepository;
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
public class UserController {
    private final UserRepository userRepository;
    private final UserModelAssembler userModelAssembler;

    public UserController(UserRepository userRepository, UserModelAssembler userModelAssembler) {
        this.userRepository = userRepository;
        this.userModelAssembler = userModelAssembler;
    }


    @GetMapping("/users")
    public CollectionModel<EntityModel<User>> all(){
        List<EntityModel<User>> users = userRepository.findAll().stream()
                .map(userModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(users, linkTo(methodOn(UserController.class).all()).withSelfRel());
    }


    @GetMapping("/user/{id}")
    public EntityModel<User> one(@PathVariable Integer id){
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return userModelAssembler.toModel(user);
    }

    @PostMapping("/user")
    public ResponseEntity<?> newCheckInRecord(@RequestBody User newUser){
        EntityModel<User> entityModel = userModelAssembler.toModel(userRepository.save(newUser));

        return ResponseEntity.
                created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).
                body(entityModel);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<?> replaceUser(@RequestBody User newUser, @PathVariable Integer id){
        User updatedUser = userRepository.findById(id)
                .map(user -> {
                    user.setFirstName(newUser.getFirstName());
                    user.setLastName(newUser.getLastName());
                    user.setUserUUID(newUser.getUserUUID());
                    return userRepository.save(user);
                })
                .orElseGet(() -> {
                    return userRepository.save(newUser);
                });

        EntityModel<User> entityModel = userModelAssembler.toModel(updatedUser);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id){
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
