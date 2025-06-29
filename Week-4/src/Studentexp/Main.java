package Studentexp;
import java.util.Scanner;
// entry point 
public class Main {
	public static void main(String args[]) {
		Scanner sc=new Scanner(System.in);
		StudentService service = new StudentService();
		// looping
		while(true) {
			System.out.println("\n1.add 2.update 3.delete 4.search 5.display 6.export csv 7.exit");
			// user choosing option
			System.out.println("enter option:");
			int option=Integer.parseInt(sc.nextLine());
			if(option==7) {
				break;
			}
			try {
				switch(option) {
				// adding student
				case 1:
					System.out.print("id:");
					int id=Integer.parseInt(sc.nextLine());
					System.out.print("name:");
					String name=sc.nextLine();
					System.out.print("age:");
					int age=Integer.parseInt(sc.nextLine());
					System.out.print("grade:");
					String grade=sc.nextLine();
					System.out.print("address:");
					String address=sc.nextLine();
					service.addStudent(new Student(id,name,age,grade,address));
					break;
				// updating student details
				case 2:
					System.out.print("id to update:");
				    id=Integer.parseInt(sc.nextLine());
					System.out.print("name:");
					name=sc.nextLine();
					System.out.print("age:");
					age=Integer.parseInt(sc.nextLine());
					System.out.print("grade:");
					grade=sc.nextLine();
					System.out.print("address:");
					address=sc.nextLine();
					service.updateStudent(new Student(id,name,age,grade,address));
					break;
				// deleting student using id
				case 3:
					System.out.print("id to delete:");
					id=Integer.parseInt(sc.nextLine());
					service.deleteStudent(id);
					break;
				// searching student using id
				case 4:
					System.out.print("id to search:");
					id=Integer.parseInt(sc.nextLine());
					service.searchStudent(id);
					break;
				// displaying students
				case 5:
					service.listStudents();
					break;
				// exporting to csv file
				case 6:
					service.exportCSV("students.csv");
					break;
				default:
					System.out.println("Invalid option");
					
					
				}
			}
			catch(Exception e) {
				System.out.println("error: "+e.getMessage());
			}
		}
		sc.close();
		System.out.println("existing");
	}

}
