package studentapp.controller;
import studentapp.dto.StudentDTO;
import studentapp.model.Student;
import studentapp.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
//Marks class as a restController which handles Http requests
@RestController
//URL for all related endpoints
@RequestMapping("/students")
// Autogenerate constructors for all final fields
@RequiredArgsConstructor
public class StudentController {
	private final StudentService service;
	// post-Adds a student
	@PostMapping
	public ResponseEntity<Student> addStudent(@Valid @RequestBody StudentDTO dto){
		// @Valid-makes request body is validated
		// call service to add students
		return new ResponseEntity<>(service.addStudent(dto),HttpStatus.CREATED);
	}
	// get - getting all students
	@GetMapping
	public ResponseEntity<List<Student>>getAllStudents(){
		// returns all students from service layer
		return ResponseEntity.ok(service.getAllStudents());
	}
	// get - gets students by id
	@GetMapping("/{id}")
	public ResponseEntity<Student> getStudentById(@PathVariable Long id){
		// returns student by ID
		return ResponseEntity.ok(service.getStudentById(id));
	}
	// put - updates student by id
	@PutMapping("/{id}")
	public ResponseEntity<Student> updateStudent(@PathVariable Long id,@Valid @RequestBody StudentDTO dto){
		// updates students with given id
		return ResponseEntity.ok(service.updateStudent(id, dto));
	}
	// delete - delete student by id
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteStudent(@PathVariable Long id){
		// deletes student with id
		service.deleteStudent(id);
		return ResponseEntity.noContent().build();
	}
}

