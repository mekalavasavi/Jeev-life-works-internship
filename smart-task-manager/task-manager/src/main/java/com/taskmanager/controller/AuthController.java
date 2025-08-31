package com.taskmanager.controller;
import com.taskmanager.dto.JwtResponse;
import com.taskmanager.dto.LoginRequest;
import com.taskmanager.dto.SignupRequest;
import com.taskmanager.entity.User;
import com.taskmanager.repository.UserRepository;
import com.taskmanager.security.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("Email is already registered");
        }
        String roleToSave = "EMPLOYEE";  
        if (request.getRole() != null) {
            String requestedRole = request.getRole().toUpperCase();
            if (List.of("ADMIN", "MANAGER", "EMPLOYEE").contains(requestedRole)) {
                roleToSave = requestedRole;
            } else {
                return ResponseEntity.badRequest().body("Invalid role");
            }
        }
        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(roleToSave)  
                .verified(true)
                .enabled(true)
                .build();
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null);
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        return ResponseEntity.ok(new JwtResponse(token));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public List<User> listAllUsers() {
        return userRepository.findAll();
    }
    // Update a user's role - Admin only
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/users/{id}/role")
    public ResponseEntity<?> updateUserRole(@PathVariable Long id, @RequestParam String role) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        if (!List.of("ROLE_ADMIN", "ROLE_MANAGER", "ROLE_EMPLOYEE").contains(role)) {
            return ResponseEntity.badRequest().body("Invalid role");
        }
        user.setRole(role);
        userRepository.save(user);
        return ResponseEntity.ok("User role updated successfully");
    }
    // Enable or disable a user - Admin only
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/users/{id}/enable")
    public ResponseEntity<?> enableUser(@PathVariable Long id, @RequestParam boolean enabled) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        user.setEnabled(enabled);
        userRepository.save(user);
        return ResponseEntity.ok("User enabled status updated");
    }

    

}
