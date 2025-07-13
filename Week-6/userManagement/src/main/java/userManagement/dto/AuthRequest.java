package userManagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * DTO for user login.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;
}

