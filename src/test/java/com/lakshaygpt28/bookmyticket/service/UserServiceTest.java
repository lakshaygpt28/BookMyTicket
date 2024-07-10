package com.lakshaygpt28.bookmyticket.service;

import com.lakshaygpt28.bookmyticket.model.User;
import com.lakshaygpt28.bookmyticket.repository.UserRepository;
import com.lakshaygpt28.bookmyticket.TestData.UserTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private List<User> dummyUsers;

    @BeforeEach
    public void setUp() {
        dummyUsers = UserTestData.getDummyUsers();
    }

    @Test
    public void addUser_ValidUser_ReturnsSavedUser() {
        User dummyUser = UserTestData.getDummyUser1();

        when(userRepository.save(any(User.class))).thenReturn(dummyUser);

        User savedUser = userService.addUser(dummyUser);

        verify(userRepository, times(1)).save(dummyUser);
        assertEquals(dummyUser.getId(), savedUser.getId());
        assertEquals(dummyUser.getName(), savedUser.getName());
    }

    @Test
    public void getUserById_ExistingUserId_ReturnsUser() {
        User dummyUser = dummyUsers.get(0);
        when(userRepository.findById(dummyUser.getId())).thenReturn(Optional.of(dummyUser));

        Optional<User> optionalUser = userService.getUserById(dummyUser.getId());

        verify(userRepository, times(1)).findById(dummyUser.getId());
        assertTrue(optionalUser.isPresent());
        assertEquals(dummyUser.getId(), optionalUser.get().getId());
        assertEquals(dummyUser.getName(), optionalUser.get().getName());
    }

    @Test
    public void getUserById_NonExistingUserId_ReturnsEmptyOptional() {
        Long nonExistingId = 999L;
        when(userRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        Optional<User> optionalUser = userService.getUserById(nonExistingId);

        verify(userRepository, times(1)).findById(nonExistingId);
        assertTrue(optionalUser.isEmpty());
    }

    @Test
    public void getAllUsers_NoUsers_ReturnsEmptyList() {
        when(userRepository.findAll()).thenReturn(List.of());

        List<User> users = userService.getAllUsers();

        verify(userRepository, times(1)).findAll();
        assertTrue(users.isEmpty());
    }

    @Test
    public void getAllUsers_MultipleUsers_ReturnsListOfUsers() {
        when(userRepository.findAll()).thenReturn(dummyUsers);

        List<User> users = userService.getAllUsers();

        verify(userRepository, times(1)).findAll();
        assertEquals(dummyUsers.size(), users.size());
        assertEquals(dummyUsers.get(0).getId(), users.get(0).getId());
        assertEquals(dummyUsers.get(1).getName(), users.get(1).getName());
    }

    @Test
    public void updateUser_ExistingUserId_ReturnsUpdatedUser() {
        User dummyUser = dummyUsers.get(0);
        User updatedUser = new User();
        updatedUser.setId(dummyUser.getId());
        updatedUser.setName("Updated Name");

        when(userRepository.findById(dummyUser.getId())).thenReturn(Optional.of(dummyUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.updateUser(dummyUser.getId(), updatedUser);

        verify(userRepository, times(1)).findById(dummyUser.getId());
        verify(userRepository, times(1)).save(any(User.class));
        assertEquals(dummyUser.getId(), result.getId());
        assertEquals(updatedUser.getName(), result.getName());
    }

    @Test
    public void deleteUser_ExistingUserId_VerifyDeletion() {
        User dummyUser = dummyUsers.get(0);
        doNothing().when(userRepository).deleteById(dummyUser.getId());

        userService.deleteUser(dummyUser.getId());

        verify(userRepository, times(1)).deleteById(dummyUser.getId());
    }

    @Test
    public void deleteUser_NonExistingUserId_NoDeletionAttempted() {
        Long nonExistingId = 999L;
        doNothing().when(userRepository).deleteById(nonExistingId);

        userService.deleteUser(nonExistingId);

        verify(userRepository, times(1)).deleteById(nonExistingId);
    }
}