package com.example.testhr;

import com.example.hr.model.User;
import com.example.hr.repository.UserRepository;
import com.example.hr.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1);
        user.setFirstName("Ajna");
        user.setLastName("Mujkić");
        user.setUuid(UUID.randomUUID());
    }

    @Test
    void testInsert() {
        doNothing().when(entityManager).persist(user);

        Integer userId = userService.insert(user);

        verify(entityManager, times(1)).persist(user);
        assertEquals(user.getId(), userId);
    }

    @Test
    void testSaveUserWhenUserExists() {
        when(userRepository.existsByUuid(user.getUuid())).thenReturn(true);

        User result = userService.saveUser(user);

        verify(userRepository, times(1)).existsByUuid(user.getUuid());
        verify(userRepository, never()).save(any(User.class));
        assertEquals(user, result);
    }

    @Test
    void testSaveUserWhenUserDoesNotExist() {
        when(userRepository.existsByUuid(user.getUuid())).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.saveUser(user);

        verify(userRepository, times(1)).existsByUuid(user.getUuid());
        verify(userRepository, times(1)).save(user);
        assertEquals(user, result);
    }
}
