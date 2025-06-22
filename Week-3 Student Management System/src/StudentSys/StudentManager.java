package StudentSys;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;
// Student manager class
public class StudentManager {
	private ArrayList<Student> students=new ArrayList<>();
	private HashMap<Integer, Student> studentMap=new HashMap<>();
	private TreeSet<Student> studentSet=new TreeSet<>();
	// adds a new student
	public void addStudent(Student student) {
		if(studentMap.containsKey(student.getId())) {
			System.out.println("Id already exists");
			return;
		}
		students.add(student);
		studentMap.put(student.getId(), student);
		studentSet.add(student);
		System.out.println("Student added successfully");
		
	}
	// removes a student using id 
	public void removeStudent(int id) {
		Student student=studentMap.get(id);
		if(student== null) {
			System.out.println("Student not found");
			return;
		}
		students.remove(student);
		studentSet.remove(student);
		studentMap.remove(id);
		
		System.out.println("Student removed Successfully");
		
	}
	// updates already existing student
	public void updateStudent(int id , String name , int age , String grade, String address) {
		Student student=studentMap.get(id);
		if(student==null) {
			System.out.println("Student not found");
			return;
		}
		
		studentSet.remove(student);
		student.setName(name);
		student.setAge(age);
		student.setGrade(grade);
		student.setAddress(address);
		studentSet.add(student);
		System.out.println("Student updated Successfully");
		
	}
	// searches for a student using id
	public Student searchStudent(int id) {
		return studentMap.get(id);
	}
	// displays all student data
	public void displayAllStudents() {
		if(studentSet.isEmpty()) {
			System.out.println("There are no students to display");
			return;
		}
		System.out.println("Student List(by id): ");
		for(Student s: studentSet) {
			System.out.println(s);
		}
		
		
	}
	// load students from available file
	public void loadFromFile(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            students = (ArrayList<Student>) in.readObject();
            studentMap.clear();
            studentSet.clear();
            for (Student s : students) {
                studentMap.put(s.getId(), s);
                studentSet.add(s);
            }
            System.out.println("Data loaded from file.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("File not found or error reading file.");
        }
    }
	// save student data to file
    public void saveToFile(String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(students);
            System.out.println("Data saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving to file.");
        }
    }
	

}
