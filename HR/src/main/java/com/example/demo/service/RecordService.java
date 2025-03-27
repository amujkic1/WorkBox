package com.example.demo.service;

import com.example.demo.model.Record;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class RecordService {

    @PersistenceContext
    private EntityManager entityManager;

    public com.example.demo.model.Record insert(com.example.demo.model.Record record) {
        entityManager.persist(record);
        return record;
    }

    public com.example.demo.model.Record findById(int id) {
        return entityManager.find(com.example.demo.model.Record.class, id);
    }

    public List<com.example.demo.model.Record> findAll() {
        TypedQuery<com.example.demo.model.Record> query = entityManager.createQuery("SELECT r FROM Record r", com.example.demo.model.Record.class);
        return query.getResultList();
    }

    public com.example.demo.model.Record update(com.example.demo.model.Record record) {
        return entityManager.merge(record);
    }

    public void delete(int id) {
        Record record = findById(id);
        if (record != null) {
            entityManager.remove(record);
        }
    }

}
