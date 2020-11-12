package entities;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.DBConnection;

public class Product {
	private static Connection connection = null;
	private int id;
	private double price;
	private int quantity;
	public Product() {
	}
	public Product(int id, double price, int quantity) {
		this.id = id;
		this.price = price;
		this.quantity = quantity;
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
	
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) throws InvalidValueException {
		if (quantity < 0) {
			this.quantity = quantity;
		} else {
			throw new InvalidValueException();
		}
	}
	@Override 
	public boolean equals(Object o) {
		if (o instanceof Product) {
			Product temp = (Product) o;
			if (temp.id == this.id)
				return true;
		}
		return false;
	}
	@Override
	public String toString() {
		return "Produit: "
				+ "\n-Identifiant: " + this.id
				+ "\n-Prix: " + this.price
				+ "\n-Quantité en stock: " + this.quantity
				+ "\n";
	}
	
	/*
	 * Data base transactions (CRUD)
	 */
	protected static boolean addProduct(Product product, int detailsId) {
		initializeConnection();
		String request = "INSERT INTO product(product_details, price, quantity) VALUES((SELECT id FROM product_details WHERE id = ?), ?, ?)";
		try {
			PreparedStatement stmt = connection.prepareStatement(request);
			stmt.setInt(1, detailsId);
			stmt.setDouble(2, product.getPrice());
			stmt.setInt(3, product.getQuantity());
			return 0 != stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("An error occured while adding product: " + e.getMessage());
			return false;
		}
	}
	protected static boolean updateProduct(Product product, int detailsId) {
		initializeConnection();
		String request = "UPDATE product SET porduct_details = ?, price = ?, quantity = ? WHERE id = ?";
		try {
			PreparedStatement stmt = connection.prepareStatement(request);
			stmt.setInt(1, detailsId);
			stmt.setDouble(2, product.getPrice());
			stmt.setInt(3, product.getQuantity());
			stmt.setInt(4, product.getId());
			return 0 != stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("An error occured while updating product: " + e.getMessage());
			return false;
		}
	}
	protected static boolean deleteProduct(Product product) {
		initializeConnection();
		String request = "DELETE FROM product WHERE id = ?";
		try {
			PreparedStatement stmt = connection.prepareStatement(request);
			stmt.setInt(1, product.getId());
			return 0 != stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("An error occured while deleting product: " + e.getMessage());
			return false;
		}
	}
	public static ArrayList<Product> getProducts() {
		initializeConnection();
		ArrayList<Product> temp = new ArrayList<Product>();
		String request = "SELECT * FROM product, product_details, author "
				+ "WHERE product.product_details = product_details.id "
				+ "AND product_details.author = author.id";
		try {
			PreparedStatement stmt = connection.prepareStatement(request);
			ResultSet results = stmt.executeQuery();
			while(results.next()) {
				temp.add(new Book(
							results.getInt(1),
							results.getDouble(3),
							results.getInt(4),
							results.getInt(5),
							new Author(results.getInt(10), results.getString(11)),
							results.getString(8),
							results.getDate(9)
						));
			}
		} catch (SQLException e) {
			System.out.println("An error occured while fetching products: " + e.getMessage());
			return null;
		}
		return temp;
	}
}
