package entities;

public class InvalidValueException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3987385127370712373L;
	public InvalidValueException() {
		super("Operation executed with an invalid value");
	}	
}
