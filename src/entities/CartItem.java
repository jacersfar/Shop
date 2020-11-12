package entities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.DBConnection;

public class CartItem {
	private int quantity;
	private Product product;
	public CartItem(int quantity, Product product) {
		this.quantity = quantity;
		this.product = product;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) throws InvalidValueException {
		if (quantity >= 1) {
			this.quantity = quantity;
		} else {
			throw new InvalidValueException();
		}
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) throws InvalidValueException {
		if (product != null) {
			this.product = product;
		} else {
			throw new InvalidValueException();
		}
	}
	/*
	 * CartItems Database transactions
	 */
	public static ArrayList<CartItem> getCartItems(int orderId) {
		ArrayList<CartItem> temp = new ArrayList<CartItem>();
		String request = "SELECT p.id, p.price, p.quantity, pd.id ,pd.title, pd.releaseDate, au.id ,au.name, ol.quantity  "
				+ "FROM product p, product_details pd, author au, order_line ol "
				+ "WHERE p.id = ol.product "
				+ "AND p.product_details = pd.id "
				+ "AND au.id = pd.author "
				+ "AND `ol`.`order` = ?";
		try {
			PreparedStatement stmt = DBConnection.getConnection().prepareStatement(request);
			stmt.setInt(1, orderId);
			ResultSet result = stmt.executeQuery();
			while (result.next()) {
				temp.add(new CartItem(
							result.getInt(9),
							new Book(
									result.getInt(1),
									result.getDouble(2),
									result.getInt(3),
									result.getInt(4),
									new Author(
											result.getInt(7),
											result.getString(8)),
									result.getString(5),
									result.getDate(6)
									)
						));
			}
			return temp;
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("An error occured while fetching lines of order: "+ e.getMessage());
			return null;
		}
	}
	public static boolean addCartItem(CartItem cartItem, int orderId) {
		String request = "INSERT INTO order_line(`product`, `quantity`, `order`) VALUES((SELECT id FROM product WHERE id = ?), ?,(SELECT id FROM `order` WHERE id = ?))";
		try {
			PreparedStatement stmt = DBConnection.getConnection().prepareStatement(request);
			stmt.setInt(1, cartItem.getProduct().getId());
			stmt.setInt(2, cartItem.getQuantity());
			stmt.setInt(3, orderId);
			return 0 != stmt.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("An error occured while adding line of order: "+ e.getMessage());
			return false;
		}
	}
}
