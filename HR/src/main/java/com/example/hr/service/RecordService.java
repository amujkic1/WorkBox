package com.example.hr.service;

import com.example.hr.model.Record;
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

    public Record insert(Record record) {
        entityManager.persist(record);
        return record;
    }

    public Record findById(int id) {
        return entityManager.find(Record.class, id);
    }

    public List<Record> findAll() {
        TypedQuery<Record> query = entityManager.createQuery("SELECT r FROM Record r", Record.class);
        return query.getResultList();
    }

    public Record update(Record record) {
        return entityManager.merge(record);
    }

    public void delete(int id) {
        Record record = findById(id);
        if (record != null) {
            entityManager.remove(record);
        }
    }

}
