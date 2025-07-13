package userManagement.controller;

import userManagement.dto.AuthRequest;
import userManagement.model.User;
import userManagement.security.JwtUtil;
import userManagement.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for User endpoints with JWT token support.
 */
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody User user) {
        String msg = service.register(user);
        return ResponseEntity.ok(msg);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody AuthRequest request) {
        User user = service.login(request);
        String token = jwtUtil.generateToken(user.getUsername());
        log.info("JWT generated for user: {}", user.getUsername());
        return ResponseEntity.ok(token);
    }

    /**
     * Profile endpoint now secured by JWT in Authorization header.
     * Use header: Authorization: Bearer <token>
     */
    @GetMapping("/profile")
    public ResponseEntity<User> getProfile(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username = jwtUtil.extractUsername(token);
        User user = service.getProfile(username);
        return ResponseEntity.ok(user);
    }
}
