package studentapp.repository;
import studentapp.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
// provides basic CRUD operations for student entity
// no need to write implementation
public interface StudentRepository extends JpaRepository<Student,Long> {

}
