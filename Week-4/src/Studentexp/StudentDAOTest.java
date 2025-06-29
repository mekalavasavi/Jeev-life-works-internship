package Studentexp;
import static org.junit.jupiter.api.Assertions.*;

public class StudentDAOTest {
	static Student testStudent;
	static StudentService service;
	public static void setup() {
		testStudent=new Student(12,"vasavi",22,"A","vijayawada");
		service = new StudentService();
	}
	// test for inserting student
	public void testInsert() {
        assertDoesNotThrow(() -> service.addStudent(testStudent));
    }
	// test for searching student
    public void testSearch() {
        assertDoesNotThrow(() -> service.searchStudent(999));
    }
    // test for updating student
    public void testUpdate() {
        testStudent.name = "UpdatedUser";
        assertDoesNotThrow(() -> service.updateStudent(testStudent));
    }
    // test for deleting student
    public void testDelete() {
        assertDoesNotThrow(() -> service.deleteStudent(999));
    }
}
