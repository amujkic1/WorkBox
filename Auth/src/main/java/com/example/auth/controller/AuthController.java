package com.example.auth.controller;

import com.example.auth.config.JwtService;
import com.example.auth.events.UserCreatedEvent;
import com.example.auth.messaging.UserEventProducer;
import com.example.auth.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService service;
    private final UserEventProducer userEventProducer;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){

        System.out.println("u /register ruti - requestttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt");
        System.out.println(request);

        var authResponse = service.register(request);

        System.out.println("authresponseeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
        System.out.println(jwtService.extractEmail(authResponse.getToken()));
        System.out.println("u AuthController jwtService.extractUnameeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
        System.out.println(jwtService.extractUname(authResponse.getToken()));

        userEventProducer.sendUserCreatedEvent(
                new UserCreatedEvent(
                        authResponse.getUuid(),
                        jwtService.extractFirstName(authResponse.getToken()),
                        jwtService.extractLastName(authResponse.getToken()),
                        jwtService.extractEmail(authResponse.getToken()),
                        jwtService.extractUname(authResponse.getToken()),
//                        jwtService.extractUsername(authResponse.getToken()),
                        jwtService.extractPassword(authResponse.getToken())
                )
        );

        return ResponseEntity.ok(
                AuthenticationResponse.builder()
                        .token(authResponse.getToken())
                        .uuid(authResponse.getUuid())
                        .build()
        );
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        var authResponse = service.authenticate(request);

        return ResponseEntity.ok(
                AuthenticationResponse.builder()
                        .token(authResponse.getToken())
                        .build()
        );
    }
}