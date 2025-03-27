package com.example.demo.service;

import com.example.demo.model.Request;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RequestService {

    @PersistenceContext
    private EntityManager entityManager;

    public Request insert(Request request) {
        entityManager.persist(request);
        return request;
    }

    public Request findById(int id) {
        return entityManager.find(Request.class, id);
    }

    public List<Request> findAll() {
        TypedQuery<Request> query = entityManager.createQuery("SELECT r FROM Request r", Request.class);
        return query.getResultList();
    }

    public Request update(Request request) {
        return entityManager.merge(request);
    }

    public void delete(int id) {
        Request request = findById(id);
        if (request != null) {
            entityManager.remove(request);
        }
    }
}