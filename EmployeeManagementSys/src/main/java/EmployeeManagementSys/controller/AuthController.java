package EmployeeManagementSys.controller;

import EmployeeManagementSys.entity.User;
import EmployeeManagementSys.repository.UserRepository;
import EmployeeManagementSys.util.JwtUtil;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword()));

        User user = userRepo.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtil.generateToken(user.getUsername(), user.getRoles());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody LoginRequest request) {
        if (userRepo.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Set.of("USER"))
                .build();

        userRepo.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class LoginRequest {
        @NotBlank
        private String username;
        @NotBlank
        private String password;
    }
}
