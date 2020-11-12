package entities;

import java.util.ArrayList;

public class Client extends User {
	private Cart cart;
	private ArrayList<Order> orders = new ArrayList<Order>();
	public Client(int id,String userName, String passWord, String name) {
		super(id,userName, passWord, name);
		this.cart = new Cart();
	}
	public Client(int id,String userName, String passWord, String name, ArrayList<Order> orders) {
		super(id, userName, passWord, name);
		this.cart = new Cart();
		this.orders = orders;
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
	public boolean addOrder(Order order) throws InvalidValueException {
		if (order != null) {
			this.orders.add(order);
			return true;
		} else {
			throw new InvalidValueException();
		}
	}
	public boolean deleteOrder(Order order) throws InvalidValueException {
		if (order != null) {
			for (Order o: this.orders) {
				if (o.getId() == order.getId()) {
					this.orders.remove(o);
					return true;
				}
			}
			return false;
		} else {
			throw new InvalidValueException();
		}
	}
	public void displayOrders() {
		for (Order order: this.orders) {
			System.out.println(order);
		}
	}
	
}
