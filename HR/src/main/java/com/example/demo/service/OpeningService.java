package com.example.demo.service;

import com.example.demo.model.Opening;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class OpeningService {

    @PersistenceContext
    private EntityManager entityManager;

    public Opening insert(Opening opening) {
        entityManager.persist(opening);
        return opening;
    }

    public Opening findById(int id) {
        return entityManager.find(Opening.class, id);
    }

    public List<Opening> findAll() {
        TypedQuery<Opening> query = entityManager.createQuery("SELECT o FROM Opening o", Opening.class);
        return query.getResultList();
    }

    public Opening update(Opening opening) {
        return entityManager.merge(opening);
    }

    public void delete(int id) {
        Opening opening = findById(id);
        if (opening != null) {
            entityManager.remove(opening);
        }
    }

}