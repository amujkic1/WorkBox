package com.example.demo;

import com.example.demo.controller.UserController;
import com.example.demo.controller.assembler.UserModelAssembler;
import com.example.demo.models.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@ExtendWith(MockitoExtension.class)
public class UserControllerTests {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserModelAssembler userModelAssembler;

    @InjectMocks
    private UserController userController;



    private User user;

    @BeforeEach
    void setUp() {
        userController = new UserController(userRepository, userModelAssembler);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        user = new User("TestIme", "TestPrezime");
    }

    @Test
    void testGetAllFeedbacks() throws Exception {


        List<User> usersList = Arrays.asList(user);
        when(userService.getAllUsers()).thenReturn(usersList);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].userId").value(user.getUserId()))
                .andExpect(jsonPath("$.links").exists());

        verify(userService, times(1)).getAllUsers();
    }
}
