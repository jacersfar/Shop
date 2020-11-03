package entities;

public class Admin extends User {
	private String name;
	public Admin() {
		super();
	}
	public Admin(String userName, String passWord, String name) {
		super(userName, passWord);
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
