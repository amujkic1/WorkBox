package com.example.demo.service;

import com.example.demo.models.User;
import com.example.demo.repository.UserRepository;
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

    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();

    }

    public void insert(User user){
        userRepository.save(user);
    }

    @Transactional
    public boolean deleteUserByUUID(UUID uuid) {
        Optional<User> userOpt = userRepository.findByUserUUID(uuid);
        if (userOpt.isPresent()) {
            userRepository.deleteByUserUUID(uuid);
            return true;
        } else {
            return false;
        }
    }


}
