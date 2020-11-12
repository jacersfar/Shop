package entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DBConnection;

public class User {
	private static Connection connection = null;
	private int id;
	private String userName;
	private String passWord;
	private String name;
	public User() {
	
	}
	public User(int id,String userName, String passWord, String name) {
		this.id = id;
		this.userName = userName;
		this.passWord = passWord;
		this.name = name;
	}
	private static void initializeConnection() {
		try {
			connection = DBConnection.getConnection();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Connecting to data base failed: \n" + e.getMessage() +"\nExiting app");
			System.exit(1);
		}
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public boolean equals(Object o) {
		if (o instanceof User) {
			if (((User) o).getId() == this.id)
				return true;
		}
		return false;
	}
	@Override
	public String toString() {
		return "-Nom: " + this.name
				+ "\nIdentifiant: " + this.userName + "\n";
	}
	/*
	 * User's database transactions
	 */
	public static User authenticate(String username, String password) {
		if (connection == null) {
			initializeConnection();
		}
		User authenticatedUser = null;
		String request = "SELECT * FROM user WHERE username = ? AND password = ?";
		try {
			PreparedStatement stmt = connection.prepareStatement(request);
			stmt.setString(1, username);
			stmt.setString(2, password);
			ResultSet result = stmt.executeQuery();
			while(result.next()) {
				if (result.getString(5).equals("ADMIN"))
					authenticatedUser = new Admin(result.getInt(1), result.getString(2), result.getString(3), result.getString(4));
				else if (result.getString(5).equals("CLIENT"))
					authenticatedUser = new Client(result.getInt(1), result.getString(2), result.getString(3), result.getString(4));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage() + "\n");
		}
		return authenticatedUser;
	}
}
