package com.example.testhr;

import com.example.hr.model.Opening;
import com.example.hr.service.OpeningService;
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
public class OpeningServiceTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private OpeningService openingService;

    private Opening opening;

    @BeforeEach
    void setUp() {
        opening = new Opening();
        opening.setId(1);
        opening.setOpeningName("Backend Developer");
    }

    @Test
    void testInsert() {
        doNothing().when(entityManager).persist(opening);

        Opening savedOpening = openingService.insert(opening);

        verify(entityManager, times(1)).persist(opening);
        assertNotNull(savedOpening);
        assertEquals(opening, savedOpening);
    }

    @Test
    void testFindById() {
        when(entityManager.find(Opening.class, 1)).thenReturn(opening);

        Opening foundOpening = openingService.findById(1);

        verify(entityManager, times(1)).find(Opening.class, 1);
        assertNotNull(foundOpening);
        assertEquals(1, foundOpening.getId());
    }

    @Test
    void testFindAll() {
        TypedQuery<Opening> mockQuery = mock(TypedQuery.class);
        List<Opening> expectedOpenings = Arrays.asList(opening);

        when(entityManager.createQuery("SELECT o FROM Opening o", Opening.class)).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(expectedOpenings);

        List<Opening> actualOpenings = openingService.findAll();

        verify(entityManager, times(1)).createQuery("SELECT o FROM Opening o", Opening.class);
        assertEquals(expectedOpenings, actualOpenings);
    }

    @Test
    void testUpdate() {
        when(entityManager.merge(opening)).thenReturn(opening);

        Opening updatedOpening = openingService.update(opening);

        verify(entityManager, times(1)).merge(opening);
        assertNotNull(updatedOpening);
        assertEquals(opening, updatedOpening);
    }

    @Test
    void testDeleteWhenExists() {
        when(entityManager.find(Opening.class, 1)).thenReturn(opening);

        openingService.delete(1);

        verify(entityManager, times(1)).remove(opening);
    }

    @Test
    void testDeleteWhenNotExists() {
        when(entityManager.find(Opening.class, 1)).thenReturn(null);

        openingService.delete(1);

        verify(entityManager, never()).remove(any(Opening.class));
    }
}
