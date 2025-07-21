package EmployeeManagementSys.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

    @GetMapping("/api/profile")
    public String profile(Authentication auth) {
        return "Hello " + auth.getName() + ", Roles: " + auth.getAuthorities();
    }
}

