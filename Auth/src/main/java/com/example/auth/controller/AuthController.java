package com.example.auth.controller;

import com.example.auth.dto.UserDTO;
import com.example.auth.config.RabbitMQProducer;
import com.example.auth.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService service;
    private final RabbitMQProducer rabbitMQProducer;


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        AuthenticationResponse response = service.register(request);

        UserDTO userDTO = new UserDTO(
                request.getFirstName(),
                request.getLastName(),
                request.getUsername(),
                request.getEmail(),
                request.getPassword(),
                request.getUuid()
        );

        System.out.println("\n\nUser je :");
        System.out.println(userDTO);
        System.out.println("--------------------------\n\n");

        rabbitMQProducer.sendJSONMessage(userDTO);

        return ResponseEntity.ok(response);
        //return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(service.authenticate(request));
    }
}