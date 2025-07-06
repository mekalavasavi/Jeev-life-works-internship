package studentapp.service;
import studentapp.model.Student;
import studentapp.dto.StudentDTO;
import java.util.List;
import org.springframework.stereotype.Service;
@Service
public interface StudentService {
	//adds a student using data from studentdto
	Student addStudent(StudentDTO dto);
	//returns a lost of students
	List<Student>getAllStudents();
	// gets a student by id
	Student getStudentById(Long id);
	// updates a student by id
	Student updateStudent(Long id , StudentDTO dto);
	// deletes a student by id
	void deleteStudent(Long id);

}
