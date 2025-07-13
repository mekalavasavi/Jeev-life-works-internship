package studentapp.dto;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
	// validates name is not null
	@NotBlank(message="name is required")
	private String name;
	// age atleast 1
	@Min(value=1,message="age must be positive")
	private int age;
	// grades must be A,B,C,D
	@Pattern(regexp="^[A-D][+-]?$",message="Grade must be A+,B,C,D,E,F")
	private String grade;
	private String address;
	
}
