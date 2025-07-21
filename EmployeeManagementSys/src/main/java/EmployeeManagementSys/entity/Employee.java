package EmployeeManagementSys.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor 
@Builder
public class Employee {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private String department;
    private double salary;
}
