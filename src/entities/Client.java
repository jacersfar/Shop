package entities;

import java.util.ArrayList;

public class Client extends User {
	private String name;
	private Cart cart;
	private ArrayList<Order> orders = new ArrayList<Order>();
	public Client() {
		super();
	}
	public Client(String userName, String passWord, String name, Cart cart, ArrayList<Order> orders) {
		super(userName, passWord);
		this.name = name;
		this.cart = cart;
		this.orders = orders;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Cart getCart() {
		return cart;
	}
	public void setCart(Cart cart) {
		this.cart = cart;
	}
	public ArrayList<Order> getOrders() {
		return orders;
	}
	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
	}
	public void displayOrders() {
		for (Order order: this.orders) {
			System.out.println(order);
		}
	}
	
}
