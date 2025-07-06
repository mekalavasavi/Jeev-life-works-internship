package studentapp.model;
import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
//marks class as jpa entity(Connects to database)
@Entity
// lombok-generates getters,setters automatically
@Data
//lombok-generates no argument constructor
@NoArgsConstructor
// lombok-generates a constructor with all arguments
@AllArgsConstructor
public class Student {
	// primary key
	@Id
	// auto-increment id in database
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	// checks name is required if it is null
	@NotBlank(message="name is required")
	private String name;
	// age atleast 1
	@Min(value=1,message="age must be positive")
	private int age;
	// grade must be A,B,C,D
    @Pattern(regexp = "^[A-D][+-]?$", message = "Grade must be A+, B, C-, etc.")
    private String grade;

    private String address;


    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setAddress(String address) {
        this.address = address;
    }


	

}
