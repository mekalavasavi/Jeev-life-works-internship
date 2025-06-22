package StudentSys;
import java.io.*;
import java.util.*;
// student class
public class Student implements Serializable , Comparable<Student>{
	private int id;
	private String name;
	private int age;
	private String grade;
	private String address;
	// constructor
	public Student(int id , String name , int age, String grade, String address) {
		this.id=id;
		this.name=name;
		this.age=age;
		this.grade=grade;
		this.address=address;
	}
	// getters and setters
	public int getId() {
		return id;
	}
	public void setName(String name) {
		 this.name=name;
	}
	public void setAge(int age) {
		this.age=age;
	}
	public void setGrade(String grade) {
		this.grade=grade;
	}
	public void setAddress(String address) {
		this.address=address;
	}
	// returning student info
	public String toString() {
		return "ID:"+ id +", Name: " + name +", Age: " + age +", Grade: " + grade + ", Address: " + address ;
	}
	
	// hashing id 
	public int hashCode() {
        return Objects.hash(id);
    }
	public int compareTo(Student s) {
        return Integer.compare(this.id, s.id); 
    }
	
	

}
