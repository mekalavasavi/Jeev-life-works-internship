package Studentexp;
import java.sql.Connection;
import java.sql.DriverManager;
// class to manage database connections
public class DBUtil {
	public static Connection getConnection() throws Exception{
		// JDBC url which is running on localhost
		String url="jdbc:mysql://localhost:3306/studentdb";
		// Database credentials
		String username="root";
		String password="Vasavi@2003";
		//establishing connection
		return DriverManager.getConnection(url,username,password);
	}

}
