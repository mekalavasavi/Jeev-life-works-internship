package employeeManagement.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * Employee entity class
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Department is mandatory")
    private String department;

    @NotBlank(message = "Position is mandatory")
    private String position;

    @Min(value = 0, message = "Salary must be positive")
    private double salary;
}

