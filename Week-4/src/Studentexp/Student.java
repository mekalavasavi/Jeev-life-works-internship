package Studentexp;
// student class
public class Student {
	//student id
	public int id;
	//student name
	public String name;
	//student age
	public int age;
	//student grade
	public String grade;
	//student address
	public String address;
	// constructor for student
	public Student(int id,String name,int age,String grade,String address) {
		this.id=id;
		this.name=name;
		this.age=age;
		this.grade=grade;
		this.address=address;
	}
	// returning student info
	public String toString() {
		return id + "-" + name + "-" + age + "-" + grade + "-" + address;
	}

}
