package entities;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DBConnection;

public class Book extends Product {
	private static Connection connection = null;
	private int bookId;
	private Author author;
	private String title;
	private Date releaseDate;
	public Book(int id, double price, int quantity,int bookId ,Author author, String title, Date releaseDate) {
		super(id, price, quantity);
		this.author = author;
		this.title = title;
		this.releaseDate = releaseDate;
		this.bookId = bookId;
	}
	private static void initializeConnection() {
		try {
			connection = DBConnection.getConnection();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Connecting to data base failed: \n" + e.getMessage() +"\nExiting app");
			System.exit(1);
		}
	}
	
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public Author getAuthor() {
		return author;
	}
	public void setAuthor(Author author) {
		this.author = author;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	@Override
	public boolean equals(Object o) {
		if (o instanceof Book) {
			if (this.getId() == ((Book)o).getId())
				return true;
		}
		return false;
	}
	@Override
	public String toString() {
		return super.toString() + "Livre: "
				+ "\n-Titre: " + this.title
				+ "\n-Nom de l'auteur: " + this.author
				+ "\n-Date de libération: " + this.releaseDate.toString() + "\n";
	}
	/*
	 * Book's DataBase transactions
	 */
	
	public static boolean addBook(Book book) {
		if (connection == null) {
			initializeConnection();
		}
		return Author.addAuthor(book.getAuthor()) && addBookDetails(book) &&addProduct(book, book.getBookId());
	}
	public static boolean updateBook(Book book) {
		if (connection == null) {
			initializeConnection();
		}
		return updateBookDetails(book) && updateProduct(book, book.getBookId());
	}
	public static boolean deleteBook(Book book) {
		if (connection == null) {
			initializeConnection();
		}
		return deleteBookDetails(book) && deleteProduct(book);
	}
	private static boolean addBookDetails(Book book) {
		String request = "INSERT INTO product_details(id, type, author, title, releaseDate) VALUES (?,?,(SELECT id FROM author WHERE id = ?),?,?)";
		try {
			PreparedStatement stmt = connection.prepareStatement(request);
			stmt.setInt(1, book.getBookId());
			stmt.setString(2,"BOOK");
			stmt.setInt(3, book.getAuthor().getId());
			stmt.setString(4, book.getTitle());
			stmt.setDate(5, book.getReleaseDate());
			return 0 != stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("An error occured while deleting author: " + e.getMessage());
			return false;
		}
	}
	private static boolean updateBookDetails(Book book) {
		String request = "UPDATE produc_details SET author = (SELECT id FROM author WHERE id = ?), title = ?, releaseDate = ? WHERE id = ?";
		try {
			PreparedStatement stmt = connection.prepareStatement(request);
			stmt.setInt(1, book.getAuthor().getId());
			stmt.setString(2, book.getTitle());
			stmt.setDate(3, book.getReleaseDate());
			stmt.setInt(4, book.getBookId());
			return 0 != stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("An error occured while deleting author: " + e.getMessage());
			return false;
		}
	}
	private static boolean deleteBookDetails(Book book) {
		String request = "DELETE FROM product_details WHERE id = ?";
		try {
			PreparedStatement stmt = connection.prepareStatement(request);
			stmt.setInt(1, book.getBookId());
			return 0 != stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("An error occured while deleting author: " + e.getMessage());
			return false;
		}
	}
	public static int getMaxIdProductDetails() {
		if (connection == null) {
			initializeConnection();
		}
		String request = "SELECT MAX(id) FROM product_details";
		int id = 0;
		try {
			PreparedStatement stmt = connection.prepareStatement(request);
			ResultSet result = stmt.executeQuery();
			while (result.next()) {
				id = result.getInt(1);
			}
		} catch ( SQLException e) {
			
		}
		return id +1;
	}
	
}
