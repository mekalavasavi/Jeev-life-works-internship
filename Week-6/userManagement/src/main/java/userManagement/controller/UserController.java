package userManagement.controller;

import userManagement.dto.AuthRequest;
import userManagement.model.User;
import userManagement.security.JwtUtil;
import userManagement.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private JwtUtil jwtUtil;
    // registers a new user
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody User user) {
        String msg = service.register(user);
        return ResponseEntity.ok(msg);
    }
    // authenticates user and generate jwt token
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody AuthRequest request) {
        User user = service.login(request);
        String token = jwtUtil.generateToken(user.getUsername());
        log.info("JWT generated for user: {}", user.getUsername());
        return ResponseEntity.ok(token);
    }
    // returns the authenticated user profile
    @GetMapping("/profile")
    public ResponseEntity<User> getProfile(@AuthenticationPrincipal String username) {
        User user = service.getProfile(username);
        return ResponseEntity.ok(user);
    }
}
