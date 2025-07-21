package EmployeeManagementSys.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id @GeneratedValue
    private Long id;
    
    @Column(unique = true)
    private String username;
    
    private String password;
    
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles;
}
