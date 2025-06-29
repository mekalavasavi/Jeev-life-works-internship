package Studentexp;

public class StudentService {
	// dao object to handle operations
	StudentDAO dao = new StudentDAO();
	public void addStudent(Student s) {
		// validating before inserting
		if(s.id<=0 || s.age<=0 || s.name.trim().isEmpty() || s.address.trim().isEmpty()) {
			throw new IllegalArgumentException("Invalid data");
		}
		// call to insert student
		dao.insert(s);
	}
	//update student details after id validation
	public void updateStudent(Student s) {
		if(s.id<=0) {
			throw new IllegalArgumentException("Invalid data");
		}
		// call to update student
		dao.update(s);
	}
	// delete student after validation id
	public void deleteStudent(int id) {
		if(id<=0) {
			throw new IllegalArgumentException("Invalid id");
		}
		// call to delete student
		dao.delete(id);
	}
	//no validation 
	public void searchStudent(int id) {
		dao.search(id);
	}
	// display all students 
	public void listStudents() {
		dao.list();
	}
	// export to CSV 
	public void exportCSV(String filename) {
		dao.exportToCSV(filename);
	}
	

}
