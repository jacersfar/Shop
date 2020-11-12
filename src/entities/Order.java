package entities;

import java.sql.Date;

import db.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Order {
	private int id;
	private String status;
	private Date date;
	private ArrayList<CartItem> productList = new ArrayList<CartItem>();
	public Order() {
	}
	public Order(int id, String status, Date date, ArrayList<CartItem> productList) {
		this.id = id;
		this.status = status;
		this.date = date;
		this.productList = productList;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public ArrayList<CartItem> getProductList() {
		return productList;
	}
	public void setProductList(ArrayList<CartItem> productList) {
		this.productList = productList;
	}
	@Override
	public String toString() {
		String temp = "Ordre:"
				+ "\n- ID = " + this.id 
				+ "\n- Status = " + this.status
				+ "\n- La date = " + this.date.toString()
				+ "\n- La liste de produits: \n";
		int index = 1;
		for (CartItem p: this.productList) {
			temp += "Produit " + index + " (Quantité: " + p.getQuantity() + ")"
					+ "\n" + p.getProduct() ;
		}
		return temp += "\n";
	}
	/*
	 * Order's database transactions 
	 */
	public static ArrayList<Order> getOrders(Client client) {
		ArrayList<Order> temp = new ArrayList<Order>();
		String request = "SELECT * FROM `order` WHERE `client` = ?";
		try {
			PreparedStatement stmt = DBConnection.getConnection().prepareStatement(request);
			stmt.setInt(1, client.getId());
			ResultSet results = stmt.executeQuery();
			while(results.next()) {
				temp.add(new Order(results.getInt(1), results.getString(4), results.getDate(3), CartItem.getCartItems(results.getInt(1))));
			}
			return temp;
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("An error occured while fetching orders: " + e.getMessage());
			return null;
		}
		
	}
	public static boolean addOrder(int clientId, Order order) {
		String request = "INSERT INTO `order`(id, client, order_date, status) VALUES(?,(SELECT id FROM product WHERE id = ?),?,?)";
		try {
			PreparedStatement stmt = DBConnection.getConnection().prepareStatement(request);
			stmt.setInt(1, order.getId());
			stmt.setInt(2, clientId);
			stmt.setDate(3, order.getDate());
			stmt.setString(4, order.getStatus());
			stmt.executeUpdate();
			addOrderLines(order);
			return true;
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("An error occured while adding orders: " + e.getMessage());
			return false;
		}
	}
	private static void addOrderLines(Order order) {
		for (CartItem item: order.getProductList()) {
			CartItem.addCartItem(item, order.getId());
		}	
	}
	
	public static int getMaxId() {
		String request = "SELECT MAX(ID) FROM  `order`";
		int id = 0;
		try {
			PreparedStatement stmt = DBConnection.getConnection().prepareStatement(request);
			ResultSet result = stmt.executeQuery();
			while(result.next()) {
				id = result.getInt(1);
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("An error occured while getting max id of orders: " + e.getMessage());
		}
		return id +1;
	}
}
