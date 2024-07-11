package com.lakshaygpt28.bookmyticket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lakshaygpt28.bookmyticket.model.User;
import com.lakshaygpt28.bookmyticket.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void getAllUsers_shouldReturnAllUsers() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(new User(1L, "Bob", null));
        users.add(new User(2L, "Alice", null));
        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Bob"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Alice"));
    }
    @Test
    void getUserById_ValidUserId_ShouldReturnUser() throws Exception {
        Long userId = 1L;
        User user = new User(userId, "Bob", null);

        when(userService.getUserById(userId)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/v1/users/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Bob"));
    }

    @Test
    void deleteUser_ValidUserId_ShouldReturnNoContent() throws Exception {
        Long userId = 1L;

        mockMvc.perform(delete("/api/v1/users/{userId}", userId))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateUser_ValidUserIdAndUser_ShouldReturnUpdatedUser() throws Exception {
        Long userId = 1L;
        User updatedUser = new User(userId, "John Updated", null);
        when(userService.updateUser(eq(userId), any(User.class))).thenReturn(updatedUser);

        mockMvc.perform(put("/api/v1/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Updated"));
    }

    @Test
    void addUser_ValidUser_ShouldReturnCreatedUser() throws Exception {
        User userToAdd = new User(null, "New User", null);
        User savedUser = new User(1L, "New User", null);

        when(userService.addUser(any(User.class))).thenReturn(savedUser);

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userToAdd)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("New User"));
    }

    // Helper method to convert object to JSON string
    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}