package com.example.hr.service;

import com.example.hr.model.Application;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ApplicationService {

    @PersistenceContext
    private EntityManager entityManager;

    public Application insert(Application application) {
        entityManager.persist(application);
        return application;
    }

    public Application findById(int id) {
        return entityManager.find(Application.class, id);
    }

    public List<Application> findAll() {
        TypedQuery<Application> query = entityManager.createQuery("SELECT a FROM Application a", Application.class);
        return query.getResultList();
    }

    public Application update(Application application) {
        return entityManager.merge(application);
    }

    public void delete(int id) {
        Application application = findById(id);
        if (application != null) {
            entityManager.remove(application);
        }
    }
}