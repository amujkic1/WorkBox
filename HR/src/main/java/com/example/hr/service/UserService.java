package com.example.hr.service;

import com.example.hr.model.User;
import com.example.hr.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class UserService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    public long insert(User user) {
        entityManager.persist(user);
        return user.getId();
    }

    public User find(long id) {
        return entityManager.find(User.class, id);
    }

    public List<User> findAll() {
        Query query = entityManager.createNamedQuery(
                "query_find_all_users", User.class);
        return query.getResultList();
    }

    public User saveUser(User user) {

        System.out.println("servisssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
        System.out.println(user);
        return userRepository.save(user);
    }
}