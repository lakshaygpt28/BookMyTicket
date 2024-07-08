package com.lakshaygpt28.bookmyticket.service;

import com.lakshaygpt28.bookmyticket.model.User;
import com.lakshaygpt28.bookmyticket.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUserById(Long id) {
        LOG.info("Fetching user with id: {}", id);
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            LOG.info("User found: {}", user.get().getId());
        } else {
            LOG.info("User not found with id: {}", id);
        }
        return user;
    }

    public User addUser(User newUser) {
        LOG.info("Adding user: {}", newUser.getId());
        User savedUser = userRepository.save(newUser);
        LOG.info("User added successfully: {}", savedUser.getId());
        return savedUser;
    }

    public List<User> getAllUsers() {
        LOG.info("Fetching all users");
        List<User> users = userRepository.findAll();
        LOG.info("All users fetched successfully");
        return users;
    }

    public void deleteUser(Long userId) {
        LOG.info("Deleting user with id: {}", userId);
        userRepository.deleteById(userId);
        LOG.info("User deleted successfully");
    }

    public User updateUser(Long userId, User updatedUser) {
        LOG.info("Updating user with id: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        user.setName(updatedUser.getName());
        User savedUser = userRepository.save(user);
        LOG.info("User updated successfully: {}", savedUser.getId());
        return savedUser;
    }
}
