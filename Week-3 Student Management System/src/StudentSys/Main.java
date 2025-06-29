package StudentSys;

import java.util.Scanner;
// main class
public class Main {
	public static void main(String args[] ){
		Scanner sc=new Scanner(System.in);
		StudentManager manager=new StudentManager();
        System.out.println("1. Add Student");
        System.out.println("2. Remove Student");
        System.out.println("3. Update Student");
        System.out.println("4. Search Student");
        System.out.println("5. Display All Students");
        System.out.println("6. Exit and Save");
        // looping
		while(true) {
			
            System.out.print("Enter option: ");
            String option = sc.nextLine();
            
            switch(option) {
            // add student
            case "1":
            	try{
            		// input data for adding student
            		System.out.print("Enter id: ");
            		int id=Integer.parseInt(sc.nextLine());
            		if(id<=0) {
            			System.out.println("Id must be positive");
            			break;
            		}
            		System.out.print("Enter name: ");
            		String name=sc.nextLine().trim();
            		if(name.isEmpty()) {
            			System.out.println("name cannot be empty");
            			break;
            		}
            		
            		System.out.print("Enter age: ");
            		int age=Integer.parseInt(sc.nextLine());
            		if(age<=0) {
            			System.out.println("Age must be positive");
            			break;
            		}
            		System.out.print("Enter grade: ");
            		String grade=sc.nextLine().trim();
            		System.out.print("Enter address: ");
            		String address=sc.nextLine().trim();
            		if(address.isEmpty()) {
            			System.out.println("Address cannot be empty");
            			break;
            		}
            		manager.addStudent(new Student(id,name,age,grade,address));
            		break;
            		
            	}
            	catch(Exception e) {
            		System.out.println("Error:"+e.getMessage());
            	}
            	break;
            // removing student
            case "2":
            	System.out.print("Enter id to remove:");
            	manager.removeStudent(Integer.parseInt(sc.nextLine()));
            	
            	break;
            // Updating existing student data
            case "3":
            	System.out.print("Enter id for updating:");
            	int newid=Integer.parseInt(sc.nextLine());
                Student existingStudent = manager.searchStudent(newid);
                if (existingStudent == null) {
                    System.out.println("Student not found");
                    break;
                }
            	System.out.print("Enter new name: ");
            	String newname=sc.nextLine();
            	System.out.print("Enter new age: ");
            	int newage=Integer.parseInt(sc.nextLine());
            	System.out.print("Enter new grade: ");
            	String newgrade=sc.nextLine();
            	System.out.print("Enter new address: ");
            	String newaddress=sc.nextLine();
            	manager.updateStudent(newid, newname, newage, newgrade, newaddress);
            	
            	break;
            // Searching student
            case "4":
            	System.out.print("Enter id for searching: ");
            	int sid=Integer.parseInt(sc.nextLine());
            	Student res=manager.searchStudent(sid);
            	if(res!=null) {
            		System.out.println("Student found: "+res);
            	}
            	else {
            	System.out.println("Student not found");
            	}
            	break;
            // displaying students
            case "5":
            	manager.displayAllStudents();
            	break;
            // save data 
            case "6":
            	manager.saveToFile("students.dat");
            	System.out.println("Existing program");
            	return;
            default:
            	System.out.println("Invalid option");
            	
            }
            
		}
		
		
	}

}
