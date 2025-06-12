package com.example.auth.service;

import com.example.auth.config.JwtService;
import com.example.auth.controller.AuthenticationRequest;
import com.example.auth.controller.AuthenticationResponse;
import com.example.auth.controller.RegisterRequest;
import com.example.auth.events.UserCreatedEvent;
import com.example.auth.messaging.UserEventProducer;
import com.example.auth.model.Role;
import com.example.auth.model.User;
import com.example.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserEventProducer userEventProducer;

    public AuthenticationResponse register(RegisterRequest request) {

        System.out.println("requesttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt");
        System.out.println(request);
        System.out.println("geterrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
        System.out.println(request.getUsername());

        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .uuid(UUID.randomUUID())
                .build();

        System.out.println("unameeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
        System.out.println(user.getUname());

        repository.save(user);
        var jwtToken = jwtService.generateToken(user);

        String uuidString = jwtService.extractUuid(jwtToken);
        UUID uuid = UUID.fromString(uuidString);

        String firstName = jwtService.extractFirstName(jwtToken);
        String lastName = jwtService.extractLastName(jwtToken);
        String email = jwtService.extractEmail(jwtToken);
        //String username = jwtService.extractUsername(jwtToken);
        System.out.println("u AuthenticationService iz jwt tokena unameeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
        String username = jwtService.extractUname(jwtToken);
        String password = jwtService.extractPassword(jwtToken);

        userEventProducer.sendUserCreatedEvent(
                new UserCreatedEvent(uuid, firstName, lastName, email, username, password)
        );

        return AuthenticationResponse.builder().token(jwtToken).uuid(uuid).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}