package Studentexp;
import java.sql.*;
import java.io.FileWriter;
import java.util.logging.Logger;
// dao for managing student 
public class StudentDAO {
	private Logger log;
	// initializing logger
	public StudentDAO() {
	    log = new LoggerUtil().getLogger();
	}
	// inserting student details to database
	public void insert(Student s) {
		try(Connection con=DBUtil.getConnection()){
			String a="INSERT INTO STUDENTS VALUES(?,?,?,?,?)";
			PreparedStatement ps=con.prepareStatement(a);
			ps.setInt(1, s.id);
			ps.setString(2, s.name);
			ps.setInt(3, s.age);
			ps.setString(4, s.grade);
			ps.setString(5, s.address);
			// execute query
			ps.executeUpdate();
			System.out.println("Student added");
			
		}
		catch(Exception e) {
			log.severe("inset error:"+e.getMessage());
			System.out.println("error:"+e.getMessage());
		}
	}
	// updating existing student details
	public void update(Student s) {
		try(Connection con=DBUtil.getConnection()){
			String a="UPDATE STUDENTS SET name=? , age=? , grade=? , address=? WHERE id=?";
			PreparedStatement ps=con.prepareStatement(a);
			ps.setString(1,s.name);
			ps.setInt(2, s.age);
			ps.setString(3, s.grade);
			ps.setString(4, s.address);
			ps.setInt(5, s.id);
			// checking the rows updated
			int rows=ps.executeUpdate();
			System.out.println(rows>0 ? "Student updated" : "Student not found");
		}
		catch(Exception e) {
			log.severe("Update error:"+e.getMessage());
		}
	}
	// delete student details by id
	public void delete(int id) {
		try(Connection con=DBUtil.getConnection()){
			String a="DELETE from STUDENTS where id=?";
			PreparedStatement ps=con.prepareStatement(a);
			ps.setInt(1, id);
			// execute query
			int rows=ps.executeUpdate();
			System.out.println(rows>0 ? "Student deleted":"Student not found");
		}
		catch(Exception e) {
			log.severe("delete error:"+e.getMessage());
		}
	}
	// search student by id
	public void search(int id) {
		Student student = null;
		try(Connection con=DBUtil.getConnection()){
			String a="SELECT * FROM STUDENTS WHERE id=?";
			PreparedStatement ps=con.prepareStatement(a);
			ps.setInt(1, id);
			ResultSet rs=ps.executeQuery();
			// checking if student found or not
			if(rs.next()) {
				student = new Student(
		                rs.getInt("id"),
		                rs.getString("name"),
		                rs.getInt("age"),
		                rs.getString("grade"),
		                rs.getString("address")
		            );
				System.out.println("Found ");
				
			}
			else {
				System.out.println("Not found");
			}
		}
		catch(Exception e) {
			log.severe("Search error:"+e.getMessage());
		}
		return;
	}
	// displaying all the students
	public void list() {
		try(Connection con=DBUtil.getConnection()){
			ResultSet rs=con.createStatement().executeQuery("Select * from students");
			while(rs.next()) {
				System.out.println(rs.getInt("id")+"-"+rs.getString("name")+"-"+rs.getInt("age")+"-"+rs.getString("grade")+"-"+rs.getString("address"));
			}
		}
		catch(Exception e) {
			log.severe("list error:"+e.getMessage());
		}
	}
	// export data to CSV File
	public void exportToCSV(String filename) {
		try(Connection con=DBUtil.getConnection();
			FileWriter writer=new FileWriter(filename)) {
				ResultSet rs=con.createStatement().executeQuery("select * from students");
				// write data to file
				writer.write("ID,Name,Age,Grade,Address\n");
				while(rs.next()) {
					writer.write(rs.getInt("id")+","+rs.getString("name")+","+rs.getInt("age")+","+rs.getString("grade")+","+rs.getString("address")+"\n");
				}
				System.out.println("Exported to csv");
			}
		
		catch(Exception e) {
			log.severe("export error:"+e.getMessage());
		}
	}
	

}
