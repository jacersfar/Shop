package entities;

import java.util.Date;
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
	
}
