package com.example.auth.service;

import com.example.auth.model.User;
import com.example.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }


    @Transactional
    public boolean deleteUserByUUID(UUID uuid) {
        Optional<User> userOpt = userRepository.findByUuid(uuid);
        if (userOpt.isPresent()) {
            userRepository.deleteByUuid(uuid);
            return true;
        } else {
            return false;
        }
    }

}


