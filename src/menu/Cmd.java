package menu;

import java.util.ArrayList;
import java.sql.Date;
import java.util.Scanner;

import entities.Admin;
import entities.Author;
import entities.Book;
import entities.Cart;
import entities.CartItem;
import entities.Client;
import entities.InvalidValueException;
import entities.Order;
import entities.Product;
import entities.User;

public class Cmd {
	public static Scanner sc = new Scanner(System.in);
	
//	public Cmd() {
//	}
	private String authenticationMenu = "*********** Authentication *********** \n";

	private String clientMenu = "*********** Client Menu ***********"
			+ "\n*********** Acceuil ***********"
			+ "\n 1) Consulter la liste des produits"
			+ "\n 2) Ajouter un produit au panier"
			+ "\n 3) Panier"
			+ "\n 4) Consulter mes ordres"
			+ "\n 0) Se deconnecter \n";
	private String clientMenuSubMenuOrder = "*********** Client Menu ***********"
			+ "\n*********** Sub Menu: Panier ***********"
			+ "\n 1) Voir panier"
			+ "\n 2) Ajouter au panier"
			+ "\n 3) Supprimer du panier"
			+ "\n 4) Voir prix totale"
			+ "\n 5) Payer"
			+ "\n 0) Retour en arrière";
	private String adminMenu = "*********** Admin Menu ***********"
			+ "\n 1) Consulter la liste des livres"
			+ "\n 2) Ajouter un nouveau livre"
			+ "\n 3) Supprimer un livre"
			+ "\n 0) Se deconnecter \n";
	
	private void deleteBook() {
		int position = -1;
		System.out.println("Quel est la position du livre a supprimer ?");
		while (position < 1 || position > Product.getProducts().size()) {
			position = sc.nextInt();
			if (position < 1 || position > Product.getProducts().size())
				System.out.println("Valeur invalide veulliez réssayer");
		}
		Book.deleteBook((Book)Product.getProducts().get(position - 1));
	}
	private void addBook() {
		sc.nextLine();
		System.out.println("Le nom de l'auteur: ");
		String author = sc.nextLine();
		System.out.println("Le titre du livre: ");
		String title = sc.nextLine();
		System.out.println("Relase date: (aaaa-mm-jj)");
		String d = sc.next();
		Date date = Date.valueOf(d);
		System.out.println("Le prix: ");
		Double price = sc.nextDouble();
		System.out.println("La quantitiee en stock: ");
		int quantity = sc.nextInt();
		Book book = new Book(1, price, quantity, Book.getMaxIdProductDetails(), new Author(Author.getMaxId(), author), title, date);
		Book.addBook(book);
	}
	private void payer(Client client) {
		Order o = new Order(Order.getMaxId(), "ACCEPTED" ,new Date(new java.util.Date().getTime()), client.getCart().getProductList());
		Order.addOrder(client.getId(), o);
	}
	private void deleteFromCart(Client client) {
		int position = -9999;
		System.out.println("Quel est la postion du produit que vous voulez supprimer ?");
		while (position < 1 || position > client.getCart().getProductList().size()) {
			position = getChoice();
			if (position < 1 || position > client.getCart().getProductList().size())
				System.out.println("Valeur invalide, veulliez ressayer !");
		}
		client.getCart().deleteFromCart(client.getCart().getProductList().get(position-1).getProduct());
	}
	private void addToCart(Client client) {
		int position = -9999;
		int quantity = -1;
		System.out.println("Quel est la postion du produit que vous voulez ajouter au panier ?");
		while (position < 1 || position > Product.getProducts().size()) {
			position = getChoice();
			if (position < 1 || position > Product.getProducts().size())
				System.out.println("Valeur invalide, veulliez ressayer !");
		}
		while (quantity < 1) {
			System.out.println("La quantité voulu");
			quantity = getChoice();
			if (quantity < 0) {
				System.out.println("Valeur invalide, veulliez ressayer !");
			}
		}
		try {
			client.getCart().addToCart(Product.getProducts().get(position -1), quantity);
		} catch (InvalidValueException e) {
			System.out.println("Echec de l'opération ");
		}
	}
	private void displayClientOrders(Client client) {
		System.out.println("La liste de mes ordes: ");
		for (Order o: Order.getOrders(client)) {
			System.out.println(o);
		}
	}
	private void displayProducts() {
		System.out.println("La liste des produits (Livres): ");
		for (Product p: Product.getProducts()) {
			System.out.println(p);
		}
	}
	private int getChoice() {
		try {
			System.out.println("Votre choix: ");
			return sc.nextInt();
		} catch (Exception e) {
			return -9999;
		}
	}
	public void launchAdminMenu(Admin admin) {
		boolean quit = false;
		while(!quit) {
			int choice = -999;
			System.out.println(this.adminMenu);
			choice = getChoice();
			switch(choice) {
				case 0:
					quit = true;
					break;
				case 1:
					this.displayProducts();
					break;
				case 2:
					this.addBook();
					break;
				case 3:
					this.deleteBook();
					break;
				default:
					System.out.println("Valeur invalide veulliez réssayer !");
					break;
			}
		}
	}
	public void launchClientSubMenuCart(Client client) {
		boolean quit = false;
		while(!quit) {
			int choice = -999;
			System.out.println(this.clientMenuSubMenuOrder);
			choice = getChoice();
			switch (choice) {
				case 0:
					quit = true;
					break;
				case 1:
					System.out.println(client.getCart());
					break;
				case 2:
					this.addToCart(client);
					break;
				case 3:
					this.deleteFromCart(client);
					break;
				case 4:
					System.out.println("Le prix totale = " + client.getCart().getTotalPrice());
					break;
				case 5:
					this.payer(client);
					break;
				default:
					System.out.println("Valeur invalide veulliez réssayer !");
					break;
					
			}
		}
	}
	public void launchClientMenu(Client client) {
		boolean quit = false;
		while (!quit) {
			int choice = -999;
			System.out.println(this.clientMenu);
			choice = getChoice();
			switch(choice) {
				case 0:
					quit = true;
					break;
				case 1:
					this.displayProducts();
					break;
				case 2:
					this.addToCart(client);
					break;
				case 3:
					this.launchClientSubMenuCart(client);
					break;
				case 4:
					this.displayClientOrders(client);
					break;
				default:
					System.out.println("Valeur invalide Veulliez réssayer ! \n");
					break;
			}
		}
	}
	public void launchAuthenticationMenu() {
		while(true) {
			System.out.println(this.authenticationMenu);
			System.out.println("Votre identifiant: ");
			String username = sc.next();
			System.out.println("\nVotre mot de passe: ");
			String password = sc.next();
			User authenticatedUser = User.authenticate(username, password);
			if (authenticatedUser instanceof Client) {
				this.launchClientMenu((Client)authenticatedUser);
			} else if (authenticatedUser instanceof Admin) {
				this.launchAdminMenu((Admin)authenticatedUser);
			} else {
				System.out.println("Identifiant ou mot de passe erronée. \nVeulliez réssayer");
			}
		}
	}	
	public static void main(String args[]) {
		Cmd c = new Cmd();
		c.launchAuthenticationMenu();
	}

	
//	private void deleteFromCart(User user) {
//		Client client = (Client) user;
//		int position = -1;
//		try {
//			System.out.println("Quel est l'ordre du produit que vous voulez ajouter au panier ?");
//			position = sc.nextInt();
//			if ((position > this.productList.size() + 1 ) && (position < 1)) {
//				throw new Exception("valeur dépasse le nombre de cases existant");
//			}
//		} catch (Exception e) {
//			System.out.println("Valeur invalide ! Opréation non réussi");
//			return;
//		}
//		client.getCart().deleteFromCart(this.productList.get(position - 1));
//	}
//	
//	private void addProductToCart(User user) {
//		Client client = (Client)user;
//		int position = -1;
//		int quantity = 0;
//		try {
//			System.out.println("Quel est l'ordre du produit que vous voulez ajouter au panier ?");
//			position = sc.nextInt();
//			if ((position > this.productList.size() + 1) && (position < 1)) {
//				throw new Exception("valeur dépasse le nombre de cases existant");
//			}
//			System.out.println("Quantité: ");
//			quantity = sc.nextInt();
//		} catch (Exception e) {
//			System.out.println("Valeur invalide ! Opréation non réussi");
//			return;
//		}
//		client.getCart().addToCart(this.productList.get(position-1), quantity);
//	}
//	
//	private void displayProductList() {
//		for(Product product: this.productList) {
//			System.out.println(product);
//		}
//	}
//	private void displayClientOrders(User user) {
//		Client client = (Client) user;
//		client.displayOrders();
//	}
//	
//	private User searchUser(String username, String password) {
//		for(User user: this.users) {
//			if ((user.getUserName().equals(username)) && (user.getPassWord().equals(password))) {
//				return user;
//			}
//		}
//		return null;
//	}
//	public void payer(User user) {
//		Client c = (Client) user;
//		c.getOrders().add(new Order(this.orderAvailableId ++, "Confirmé", new Date(), c.getCart().getProductList()));
//		c.setCart(new Cart());
//	}
//	
//	public void launchSubMenuCart(User user) {
//		Client authenticatedUser = (Client) user;
//		boolean closeMenu = false;
//		while (!closeMenu) {
//			System.out.println(this.clientMenuSubMenuOrder);
//			int option = -1;
//			try {
//				option = sc.nextInt();
//			} catch (Exception e) {
//				System.out.println("Valeur invalide ! Veulliez réessayer !");
//				continue;
//			}
//			switch(option) {
//				case 0:
//					System.exit(1);
//					break;
//				case 1:
//					System.out.println(authenticatedUser.getCart());
//					break;
//				case 2:
//					this.addProductToCart(user);
//					break;
//				case 3:
//					this.deleteFromCart(user);
//					break;
//				case 4:
//					System.out.println("Le prix totale = " + authenticatedUser.getCart().getTotalPrice());
//					break;
//				case 5:
//					// Une simulation de paiment instantané 
//					this.payer(authenticatedUser);
//					closeMenu = true;
//					break;
//				case 6:
//					closeMenu = true;
//					break;
//			}
//		}
//	}
//	
//	public void launchClientMenu(User user) {
//		boolean closeMenu = false;
//		while (!closeMenu) {
//			System.out.println(this.clientMenu);
//			boolean valid = false;
//			int commande = -1;
//			// Getting user input and repeating if not valid
//			while (!valid) {
//				try {
//					commande = sc.nextInt();
//					valid = true;
//				} catch (Exception e) {
//					valid = false;
//				}
//			}
//			switch(commande) {
//				case 0:
//					System.exit(0);
//					break;
//				case 1: 
//					this.displayProductList();
//					break;
//				case 2: 
//					this.addProductToCart(user);
//					break;
//				case 3:
//					this.launchSubMenuCart(user);
//					break;
//				case 4:
//					this.displayClientOrders(user);
//					break;
//				default:
//					System.out.println("Reponse invalide veulliez ressaiyer ");
//					break;
//			}
//			
//		}
//		
//	}
//	public void launchAdminMenu(User user) {
//		while (true) {
//			System.out.println(this.adminMenu);
//			boolean valid = false;
//			int option = -5;
//			while (!valid) {
//				try {
//					option = sc.nextInt();
//					if (option > 4 && option <0) {
//						throw new Exception("Valeur invalide");
//					}
//					valid = true;
//				} catch (Exception e) {
//					System.out.println("Valeur inséré est invalide \n Veulliez réessayer !");
//					valid = false;
//				}
//			}
//			switch (option) {
//				case 0:
//					System.exit(0);
//					break;
//				case 1:
//					this.displayProductList();
//					break;
//				case 2:
//					this.addProductToList();
//					break;
//				case 3:
//					this.deleteProductFromlist();
//					break;
//			}
//			
//			
//		}
//	}
//	public void deleteProductFromlist() {
//		System.out.println("Quel est la position du produit a supprimer ?: ");
//		int position = -5;
//		try {
//			position = sc.nextInt();
//			if (position > this.productList.size() + 1 && position < 1)
//				throw new Exception("Valeur invalide");
//			this.productList.remove(position -1);
//		} catch (Exception e) {
//			System.out.println("Valeur invalide, Echec de l'opération !");
//		}
//	}
//	public void addProductToList() {
//		System.out.println("Le nom du nouveau produit: ");
//		String nom = sc.next();
//		System.out.println("Le type du nouveau produit: ");
//		String type = sc.next();
//		boolean valid = false;
//		double price = 0.0d;
//		int quantity = 0;
//		while (!valid) {
//			try {
//				System.out.println("Le prix du nouveau produit:  ");
//				price = sc.nextDouble();
//				if (price < 0)
//					throw new Exception("Valeur invalide");
//				System.out.println("La quantité disponible: ");
//				quantity = sc.nextInt();
//				if (quantity < 0)
//					throw new Exception("Valeur invalide");
//				valid = true;
//				
//			} catch (Exception e) {
//				System.out.println("Valeur invalide réessayer de nouveau !");
//				valid = false;
//			}
//		}
//		System.out.println("La desciption du nouveau produit: ");
//		String desc = sc.nextLine();
////		this.productList.add(new Product(this.availabeId++, nom, type, price, quantity, desc));
//	}
//	public void launch() {
//		while (true) {
//			// boolean authenticated = false;
//			System.out.println(this.authenticationMenu);			
//			System.out.println("Votre Identifiant: ");
//			String username = sc.nextLine();
//			System.out.println("Votre mot de passe: ");
//			String password = sc.nextLine();
//			User authenticatedUser = this.searchUser(username, password);
//			if (authenticatedUser != null) {
//				if (authenticatedUser instanceof Admin)
//					this.launchAdminMenu(authenticatedUser);
//				else
//					this.launchClientMenu(authenticatedUser);
//			} else {
//				System.out.println("Identifiant ou mot de passer erronées! ");
//				System.out.println("Veulliez réessayer");
//			}
//		}
//	}
//	
//	public static void main(String args[]) {
//		Cmd cmd = new Cmd();
//		cmd.launch();
//	}
//	
//	public static void main(String args[]) {
//		for (Product p: Product.getProducts()) {
//			System.out.println(p);
//		}
//		Book.addBook(new Book(10, 1500d, 1500, 10, new Author(10,"Fathi"), "I am awesome", new Date(1555)));
//		for (Product p: Product.getProducts()) {
//			System.out.println(p);
//		}
//		for (Order o: Order.getOrders((Client)User.authenticate("hammouda", "123456"))) {
//			System.out.println(o);
//		}
//		ArrayList<CartItem> items = new ArrayList<CartItem>();
//		items.add(new CartItem(50,Product.getProducts().get(0)));
//		items.add(new CartItem(40,Product.getProducts().get(1)));
//		Order o = new Order();
//		o.setId(1);
//		o.setDate(new Date(0));
//		o.setStatus("ONHOLD");
//		o.setProductList(items);
//		Order.addOrder(Client.authenticate("hammouda", "123456").getId(), o);
//		for (Order c: Order.getOrders((Client)User.authenticate("hammouda", "123456"))) {
//			System.out.println(c);
//		}
//	}
	
	
		
}
