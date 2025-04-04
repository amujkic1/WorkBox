package com.example.testhr;
import com.example.hr.model.Application;
import com.example.hr.service.ApplicationService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
        import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ApplicationServiceTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private ApplicationService applicationService;

    private Application application;

    @BeforeEach
    void setUp() {
        application = new Application();
        application.setId(1);
        application.setFirstName("New Candidate");
    }

    @Test
    void testInsert() {
        doNothing().when(entityManager).persist(application);

        Application savedApplication = applicationService.insert(application);

        verify(entityManager, times(1)).persist(application);
        assertNotNull(savedApplication);
    }

    @Test
    void testFindById() {

        when(entityManager.find(Application.class, 1)).thenReturn(application);

        Application foundApplication = applicationService.findById(1);

        assertNotNull(foundApplication);
        assertEquals(1, foundApplication.getId());
    }

    @Test
    void testUpdate() {

        when(entityManager.merge(application)).thenReturn(application);

        Application updatedApplication = applicationService.update(application);

        verify(entityManager, times(1)).merge(application);
        assertNotNull(updatedApplication);
    }

    @Test
    void testDelete() {

        when(entityManager.find(Application.class, 1)).thenReturn(application);

        applicationService.delete(1);

        verify(entityManager, times(1)).remove(application);

    }

}