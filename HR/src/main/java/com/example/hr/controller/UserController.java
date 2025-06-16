package com.example.hr.controller;

import com.example.hr.assembler.UserModelAssembler;
import com.example.hr.dto.UserDTO;
import com.example.hr.exception.UserNotFoundException;
import com.example.hr.model.Opening;
import com.example.hr.model.Request;
import com.example.hr.model.User;
import com.example.hr.repository.OpeningRepository;
import com.example.hr.repository.RequestRepository;
import com.example.hr.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@Tag(name="UserController", description = "API for user management")
public class UserController {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UserModelAssembler userModelAssembler;
    private final OpeningRepository openingRepository;
    private final RequestRepository requestRepository;

    public UserController(UserRepository userRepository, ModelMapper modelMapper,
                          UserModelAssembler userModelAssembler, OpeningRepository openingRepository,
                            RequestRepository requestRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.userModelAssembler = userModelAssembler;
        this.openingRepository = openingRepository;
        this.requestRepository = requestRepository;
    }

    @GetMapping("/users")
    @Operation(summary = "Retreive all users", description = "Returns a list of all users")
    public CollectionModel<EntityModel<UserDTO>> all(){
        List<EntityModel<UserDTO>> users = userRepository.findAll().stream()
                .map(user -> userModelAssembler.toModel(modelMapper.map(user, UserDTO.class)))
                .collect(Collectors.toList());
        return CollectionModel.of(users, linkTo(methodOn(UserController.class).all()).withSelfRel());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve a record by ID", description = "Fetches details of a specific record by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Record found"),
            @ApiResponse(responseCode = "404", description = "Record not found")
    })
    public EntityModel<UserDTO> one(@PathVariable Integer id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return userModelAssembler.toModel(userDTO);
    }

    @PostMapping
    @Operation(summary = "Create a new user")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
        User savedUser = userRepository.save(user);
        UserDTO dto = modelMapper.map(savedUser, UserDTO.class);
        EntityModel<UserDTO> entityModel = userModelAssembler.toModel(dto);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing user")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @Valid @RequestBody User userDetails) {
        User updatedUser = userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setUsername(userDetails.getUsername());
                    existingUser.setPassword(userDetails.getPassword());
                    existingUser.setRole(userDetails.getRole());
                    return userRepository.save(existingUser);
                }).orElseThrow(() -> new RuntimeException("User not found with id " + id));

        UserDTO dto = modelMapper.map(updatedUser, UserDTO.class);
        return ResponseEntity.ok(userModelAssembler.toModel(dto));
    }

    @Transactional
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user", description = "Deletes a user by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User with ID " + id + " not found.");
        }

        User user = userOptional.get();

        List<Request> requests = requestRepository.findAllByUser(user);
        requestRepository.deleteAll(requests);

        // 1. Remove user from user_openings (ManyToMany)
        for (Opening opening : user.getOpenings()) {
            opening.getUsers().remove(user);
        }

        user.getOpenings().clear();

        // 2. Remove user from Opening
        List<Opening> openingWithOwnership = openingRepository.findAllByUser(user);
        for (Opening o : openingWithOwnership) {
            o.setUser(null);
        }

        // 3. Finally delete the user
        userRepository.delete(user);

        return ResponseEntity.ok("User with ID " + id + " has been deleted successfully.");
    }

}