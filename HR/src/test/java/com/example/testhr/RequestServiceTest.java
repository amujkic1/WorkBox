package com.example.testhr;

import com.example.hr.model.Request;
import com.example.hr.service.RequestService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class RequestServiceTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private RequestService requestService;

    private Request request;

    @BeforeEach
    void setUp() {
        request = Request.builder()
                .id(1)
                .type("Vacation")
                .text("Annual leave for 10 days")
                .date(new Date())
                .status("Pending")
                .build();
    }


    @Test
    void testInsert() {
        doNothing().when(entityManager).persist(request);

        Request savedRequest = requestService.insert(request);

        verify(entityManager, times(1)).persist(request);
        assertNotNull(savedRequest);
        assertEquals(request, savedRequest);
    }

    @Test
    void testFindById() {
        when(entityManager.find(Request.class, 1)).thenReturn(request);

        Request foundRequest = requestService.findById(1);

        verify(entityManager, times(1)).find(Request.class, 1);
        assertNotNull(foundRequest);
        assertEquals(1, foundRequest.getId());
    }

    @Test
    void testFindAll() {
        TypedQuery<Request> mockQuery = mock(TypedQuery.class);
        List<Request> expectedRequests = Arrays.asList(request);

        when(entityManager.createQuery("SELECT r FROM Request r", Request.class)).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(expectedRequests);

        List<Request> actualRequests = requestService.findAll();

        verify(entityManager, times(1)).createQuery("SELECT r FROM Request r", Request.class);
        assertEquals(expectedRequests, actualRequests);
    }

    @Test
    void testUpdate() {
        when(entityManager.merge(request)).thenReturn(request);

        Request updatedRequest = requestService.update(request);

        verify(entityManager, times(1)).merge(request);
        assertNotNull(updatedRequest);
        assertEquals(request, updatedRequest);
    }

    @Test
    void testDeleteWhenExists() {
        when(entityManager.find(Request.class, 1)).thenReturn(request);

        requestService.delete(1);

        verify(entityManager, times(1)).remove(request);
    }

    @Test
    void testDeleteWhenNotExists() {
        when(entityManager.find(Request.class, 1)).thenReturn(null);

        requestService.delete(1);

        verify(entityManager, never()).remove(any(Request.class));
    }
}
