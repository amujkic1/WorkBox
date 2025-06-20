package com.example.demo.service;

import com.example.demo.models.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();

    }

    public void insert(User user){
        userRepository.save(user);
    }

    public User saveUser(User user) {
        Optional<User> existingUser = userRepository.findByUserUUID(user.getUserUUID());

        if (existingUser.isPresent()) {
            System.out.println("Korisnik već postoji: " + user.getUserUUID());
            return existingUser.get();
        }


        return userRepository.save(user);
    }

}
