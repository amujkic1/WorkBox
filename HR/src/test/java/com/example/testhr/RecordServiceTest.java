package com.example.testhr;

import com.example.hr.model.Record;
import com.example.hr.service.RecordService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class RecordServiceTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private RecordService recordService;

    private Record record;

    @BeforeEach
    void setUp() {
        record = new Record();
        record.setId(1);
        record.setJmbg(Long.valueOf("1234567890123"));
    }

    @Test
    void testInsert() {
        doNothing().when(entityManager).persist(record);

        Record savedRecord = recordService.insert(record);

        verify(entityManager, times(1)).persist(record);
        assertNotNull(savedRecord);
        assertEquals(record, savedRecord);
    }

    @Test
    void testFindById() {
        when(entityManager.find(Record.class, 1)).thenReturn(record);

        Record foundRecord = recordService.findById(1);

        verify(entityManager, times(1)).find(Record.class, 1);
        assertNotNull(foundRecord);
        assertEquals(1, foundRecord.getId());
    }

    @Test
    void testFindAll() {
        TypedQuery<Record> mockQuery = mock(TypedQuery.class);
        List<Record> expectedRecords = Arrays.asList(record);

        when(entityManager.createQuery("SELECT r FROM Record r", Record.class)).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(expectedRecords);

        List<Record> actualRecords = recordService.findAll();

        verify(entityManager, times(1)).createQuery("SELECT r FROM Record r", Record.class);
        assertEquals(expectedRecords, actualRecords);
    }

    @Test
    void testUpdate() {
        when(entityManager.merge(record)).thenReturn(record);

        Record updatedRecord = recordService.update(record);

        verify(entityManager, times(1)).merge(record);
        assertNotNull(updatedRecord);
        assertEquals(record, updatedRecord);
    }

    @Test
    void testDeleteWhenExists() {
        when(entityManager.find(Record.class, 1)).thenReturn(record);

        recordService.delete(1);

        verify(entityManager, times(1)).remove(record);
    }

    @Test
    void testDeleteWhenNotExists() {
        when(entityManager.find(Record.class, 1)).thenReturn(null);

        recordService.delete(1);

        verify(entityManager, never()).remove(any(Record.class));
    }
}
