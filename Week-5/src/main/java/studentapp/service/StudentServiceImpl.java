package studentapp.service;
import studentapp.dto.StudentDTO;
import studentapp.model.Student;
import studentapp.repository.StudentRepository;
import studentapp.exception.StudentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
//lombok-autogenerates constructors for final field
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService{
	// injecting the student repository used for access database
	private final StudentRepository repository ;
	
	@Override
	// Creates a new Student object from DTO
	public Student addStudent(StudentDTO dto) {
		Student student=new Student(null,dto.getName(),dto.getAge(),dto.getGrade(),dto.getAddress());
		// saves the student data to database and returns saved entity
		return repository.save(student);
	}
	@Override
	public List<Student> getAllStudents(){
		// retrieve and return all students
		return repository.findAll();
	}
	@Override
	public Student getStudentById(Long id) {
		// find student id , if not return not found
		return repository.findById(id)
				.orElseThrow(()->new StudentNotFoundException("student not found"));
	}
	@Override
	public Student updateStudent(Long id,StudentDTO dto) {
		// fetch existing data
		Student student=getStudentById(id);
		// updates the required fields
		student.setName(dto.getName());
		student.setAge(dto.getAge());
		student.setGrade(dto.getGrade());
		student.setAddress(dto.getAddress());
		//save and return data
		return repository.save(student);
	}
	@Override
	public void deleteStudent(Long id) {
		// fetch and delete the student
		Student student=getStudentById(id);
		repository.delete(student);
	}
}
