package entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Cmd {
	public static Scanner sc = new Scanner(System.in);
	private ArrayList<User> users = new ArrayList<User>();
	private ArrayList<Product> productList = new ArrayList<Product>();
	private int availabeId = 3;
	private int orderAvailableId = 1;
	public Cmd() {
		this.users.add(new Admin("jacersfar", "123456", "Jacer sfar"));
		this.users.add(new Client("Sassihammouda", "123456789", "Sassi ben hammouda", new Cart(), new ArrayList<Order>()));
		this.productList.add(new Product(1,"Brosse a dents", "Produit d'hygienne", 1.5d, 7500, "Couleur: rouge"));
		this.productList.add(new Product(2,"Rouge a lêvres", "Produits cométiques", 131.5d, 25, "Couleur: rouge"));
	}
	private String authenticationMenu = "*********** Authentication *********** \n";

	private String clientMenu = "*********** Client Menu ***********"
			+ "\n*********** Acceuil ***********"
			+ "\n 1) Consulter la liste des produits"
			+ "\n 2) Ajouter un produit au panier"
			+ "\n 3) Panier"
			+ "\n 4) Consulter mes ordres"
			+ "\n 0) Quitter \n";
	private String clientMenuSubMenuOrder = "*********** Client Menu ***********"
			+ "\n*********** Sub Menu: Panier ***********"
			+ "\n 1) Voir panier"
			+ "\n 2) Ajouter au panier"
			+ "\n 3) Supprimer du panier"
			+ "\n 4) Voir prix totale"
			+ "\n 5) Payer"
			+ "\n 6) Retour en arrière"
			+ "\n 0) Quitter \n";
	private String adminMenu = "*********** Admin Menu ***********"
			+ "\n 1) Consulter la liste des produits"
			+ "\n 2) Ajouter un nouveau produit"
			+ "\n 3) Supprimer un produit"
			+ "\n 0) Quitter \n";
	
	private void deleteFromCart(User user) {
		Client client = (Client) user;
		int position = -1;
		try {
			System.out.println("Quel est l'ordre du produit que vous voulez ajouter au panier ?");
			position = sc.nextInt();
			if ((position > this.productList.size() + 1 ) && (position < 1)) {
				throw new Exception("valeur dépasse le nombre de cases existant");
			}
		} catch (Exception e) {
			System.out.println("Valeur invalide ! Opréation non réussi");
			return;
		}
		client.getCart().deleteFromCart(this.productList.get(position - 1));
	}
	
	private void addProductToCart(User user) {
		Client client = (Client)user;
		int position = -1;
		int quantity = 0;
		try {
			System.out.println("Quel est l'ordre du produit que vous voulez ajouter au panier ?");
			position = sc.nextInt();
			if ((position > this.productList.size() + 1) && (position < 1)) {
				throw new Exception("valeur dépasse le nombre de cases existant");
			}
			System.out.println("Quantité: ");
			quantity = sc.nextInt();
		} catch (Exception e) {
			System.out.println("Valeur invalide ! Opréation non réussi");
			return;
		}
		client.getCart().addToCart(this.productList.get(position-1), quantity);
	}
	
	private void displayProductList() {
		for(Product product: this.productList) {
			System.out.println(product);
		}
	}
	private void displayClientOrders(User user) {
		Client client = (Client) user;
		client.displayOrders();
	}
	
	private User searchUser(String username, String password) {
		for(User user: this.users) {
			if ((user.getUserName().equals(username)) && (user.getPassWord().equals(password))) {
				return user;
			}
		}
		return null;
	}
	public void payer(User user) {
		Client c = (Client) user;
		c.getOrders().add(new Order(this.orderAvailableId ++, "Confirmé", new Date(), c.getCart().getProductList()));
		c.setCart(new Cart());
	}
	
	public void launchSubMenuCart(User user) {
		Client authenticatedUser = (Client) user;
		boolean closeMenu = false;
		while (!closeMenu) {
			System.out.println(this.clientMenuSubMenuOrder);
			int option = -1;
			try {
				option = sc.nextInt();
			} catch (Exception e) {
				System.out.println("Valeur invalide ! Veulliez réessayer !");
				continue;
			}
			switch(option) {
				case 0:
					System.exit(1);
					break;
				case 1:
					System.out.println(authenticatedUser.getCart());
					break;
				case 2:
					this.addProductToCart(user);
					break;
				case 3:
					this.deleteFromCart(user);
					break;
				case 4:
					System.out.println("Le prix totale = " + authenticatedUser.getCart().getTotalPrice());
					break;
				case 5:
					// Une simulation de paiment instantané 
					this.payer(authenticatedUser);
					closeMenu = true;
					break;
				case 6:
					closeMenu = true;
					break;
			}
		}
	}
	
	public void launchClientMenu(User user) {
		boolean closeMenu = false;
		while (!closeMenu) {
			System.out.println(this.clientMenu);
			boolean valid = false;
			int commande = -1;
			// Getting user input and repeating if not valid
			while (!valid) {
				try {
					commande = sc.nextInt();
					valid = true;
				} catch (Exception e) {
					valid = false;
				}
			}
			switch(commande) {
				case 0:
					System.exit(0);
					break;
				case 1: 
					this.displayProductList();
					break;
				case 2: 
					this.addProductToCart(user);
					break;
				case 3:
					this.launchSubMenuCart(user);
					break;
				case 4:
					this.displayClientOrders(user);
					break;
				default:
					System.out.println("Reponse invalide veulliez ressaiyer ");
					break;
			}
			
		}
		
	}
	public void launchAdminMenu(User user) {
		while (true) {
			System.out.println(this.adminMenu);
			boolean valid = false;
			int option = -5;
			while (!valid) {
				try {
					option = sc.nextInt();
					if (option > 4 && option <0) {
						throw new Exception("Valeur invalide");
					}
					valid = true;
				} catch (Exception e) {
					System.out.println("Valeur inséré est invalide \n Veulliez réessayer !");
					valid = false;
				}
			}
			switch (option) {
				case 0:
					System.exit(0);
					break;
				case 1:
					this.displayProductList();
					break;
				case 2:
					this.addProductToList();
					break;
				case 3:
					this.deleteProductFromlist();
					break;
			}
			
			
		}
	}
	public void deleteProductFromlist() {
		System.out.println("Quel est la position du produit a supprimer ?: ");
		int position = -5;
		try {
			position = sc.nextInt();
			if (position > this.productList.size() + 1 && position < 1)
				throw new Exception("Valeur invalide");
			this.productList.remove(position -1);
		} catch (Exception e) {
			System.out.println("Valeur invalide, Echec de l'opération !");
		}
	}
	public void addProductToList() {
		System.out.println("Le nom du nouveau produit: ");
		String nom = sc.next();
		System.out.println("Le type du nouveau produit: ");
		String type = sc.next();
		boolean valid = false;
		double price = 0.0d;
		int quantity = 0;
		while (!valid) {
			try {
				System.out.println("Le prix du nouveau produit:  ");
				price = sc.nextDouble();
				if (price < 0)
					throw new Exception("Valeur invalide");
				System.out.println("La quantité disponible: ");
				quantity = sc.nextInt();
				if (quantity < 0)
					throw new Exception("Valeur invalide");
				valid = true;
				
			} catch (Exception e) {
				System.out.println("Valeur invalide réessayer de nouveau !");
				valid = false;
			}
		}
		System.out.println("La desciption du nouveau produit: ");
		String desc = sc.nextLine();
		this.productList.add(new Product(this.availabeId++, nom, type, price, quantity, desc));
	}
	public void launch() {
		while (true) {
			// boolean authenticated = false;
			System.out.println(this.authenticationMenu);			
			System.out.println("Votre Identifiant: ");
			String username = sc.nextLine();
			System.out.println("Votre mot de passe: ");
			String password = sc.nextLine();
			User authenticatedUser = this.searchUser(username, password);
			if (authenticatedUser != null) {
				if (authenticatedUser instanceof Admin)
					this.launchAdminMenu(authenticatedUser);
				else
					this.launchClientMenu(authenticatedUser);
			} else {
				System.out.println("Identifiant ou mot de passer erronées! ");
				System.out.println("Veulliez réessayer");
			}
		}
	}
	
	public static void main(String args[]) {
		Cmd cmd = new Cmd();
		cmd.launch();
	}
}
