package entities;

import java.util.ArrayList;

public class Cart {
	private ArrayList<CartItem> productList;
	public Cart () {
		this.productList = new ArrayList<CartItem>();
	}
	public Cart(ArrayList<CartItem> productList) {
		this.productList = productList;
	}
	public void addToCart(Product product, int quantity) throws InvalidValueException {
		for (CartItem i: this.productList) {
			if (i.getProduct().equals(product)) {
				i.setQuantity(quantity + i.getQuantity());
				return;
			}
		}
		this.productList.add(new CartItem(quantity, product));
		
	}
	public void deleteFromCart(Product p) {
		for (CartItem c: this.productList) {
			if (c.getProduct().equals(p)) {
				this.productList.remove(c);
				break;
			}
		}
	}
	public void updateQuantity (Product product, int quantity) {
		for (CartItem c: this.productList) {
			if (c.getProduct().equals(product)) {
				int index = this.productList.indexOf(c);
				this.productList.remove(index);
				this.productList.add(index, new CartItem(quantity, product));
				break;
			}
		}
	}
	public ArrayList<Product> getProductListToProduct() {
		ArrayList<Product> temp = new ArrayList<Product>();
		for (CartItem cI: this.productList) {
			temp.add(cI.getProduct());
		}
		return temp;
	}
	
	public ArrayList<CartItem> getProductList() {
		return productList;
	}
	public void setProductList(ArrayList<CartItem> productList) {
		this.productList = productList;
	}
	public boolean verifyAvailability(Product p, int quantity) {
		if (p.getQuantity() >= quantity)
			return true;
		return false;
	}
	public double getTotalPrice() {
		double total = 0d;
		for (CartItem cartItem: this.productList)
			total += (cartItem.getQuantity() * cartItem.getProduct().getPrice());
		return total;
	}
	@Override
	public String toString() {
		String temp = "La liste des produits dans le panier: "
				+ "\n";
		int index = 1;
		for (CartItem p: this.productList) {
			temp += "Produit " + index + " (Quantité: " + p.getQuantity() + ")\n" + p.getProduct();
			index++;
		}
		return temp + "\n\n";
	}
}
