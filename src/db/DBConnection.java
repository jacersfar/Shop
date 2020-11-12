package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
// Implementing the singleton pattern to always get one and the only one connection
public class DBConnection {
	private static Connection connection = null;
	// Creating instances of this Class is not allowed
	private DBConnection() {
	}
	private static void createConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/store?useSSL=false", "root", "");	
	}
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		if (connection == null) {
			createConnection();
		}
		return connection;
	}
}
