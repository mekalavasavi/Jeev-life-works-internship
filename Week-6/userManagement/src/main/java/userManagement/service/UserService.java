package userManagement.service;

import userManagement.dto.AuthRequest;
import userManagement.exception.*;
import userManagement.model.User;
import userManagement.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service to handle user-related operations.
 */
@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public String register(User user) {
        repository.findByEmail(user.getEmail()).ifPresent(u -> {
            throw new UserAlreadyExistsException("Email already registered.");
        });

        repository.findByUsername(user.getUsername()).ifPresent(u -> {
            throw new UserAlreadyExistsException("Username already exists.");
        });

        repository.save(user);
        log.info("User registered successfully: {}", user.getUsername());
        return "Registration successful";
    }

    public User login(AuthRequest request) {
        return repository.findByUsernameAndPassword(request.getUsername(), request.getPassword())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid username or password"));
    }

    public User getProfile(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}

