package com.example.business.service;

import com.example.business.model.User;
import com.example.business.repository.UserRepository;
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

    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
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

