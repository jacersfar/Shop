package entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DBConnection;

public class Author {
	private static Connection connection = null;
	private int id;
	private String name;
	public Author(int id, String name) {
		this.id = id;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return this.name;
	}
	/*
	 * Author database transactions
	 */
	public static boolean addAuthor(Author author) {
		initializeConnection();
		String request = "INSERT INTO author(id, name) VALUES(?,?)";
		try {
			PreparedStatement stmt = connection.prepareStatement(request);
			stmt.setString(2, author.getName());
			stmt.setInt(1, author.getId());
			int number = stmt.executeUpdate();
			System.out.println(number);
			return 0 != number;
		} catch (SQLException e) {
			System.out.println("An error occured while adding author: " + e.getMessage());
			return false;
		}
	}
	public static boolean updateAuthor(Author author) {
		initializeConnection();
		String request = "UPDATE author SET name = ? WHERE id = ?";
		try {
			PreparedStatement stmt = connection.prepareStatement(request);
			stmt.setString(1, author.getName());
			stmt.setInt(2, author.getId());
			return 0 != stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("An error occured while updating author: " + e.getMessage());
			return false;
		}
	}
	public static boolean deleteAuthor(Author author) {
		initializeConnection();
		String request = "DELETE FROM author WHERE id = ?";
		try {
			PreparedStatement stmt = connection.prepareStatement(request);
			stmt.setInt(1, author.getId());
			return 0 != stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("An error occured while deleting author: " + e.getMessage());
			return false;
		}
	}
	public static int getMaxId() {
		initializeConnection();
		int id = 0;
		String request = "SELECT MAX(id) FROM author";
		try {
			PreparedStatement stmt = connection.prepareStatement(request);
			ResultSet result = stmt.executeQuery();
			while(result.next()) {
				id = result.getInt(1);
			}
		} catch(SQLException e) {
			
		}
		return id + 1;
	}
}
