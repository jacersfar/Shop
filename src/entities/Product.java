package entities;

public class Product {
	private int id;
	private String name;
	private String type;
	private double price;
	private int quantity;
	private String description;
	
	public Product() {
	}
	public Product(int id, String name, String type, double price, int quantity, String description) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.price = price;
		this.quantity = quantity;
		this.description = description;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Produit: \n" + "- nom = " + this.name + "\n- Type = " + this.type
				+ "\n- Prix = " + this.price + "\n- Quantité disponible = " + this.quantity + "\n\n";
		
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
}
