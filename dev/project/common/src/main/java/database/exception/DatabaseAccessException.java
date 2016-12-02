package database.exception;

public class DatabaseAccessException extends Exception {

	private static final long serialVersionUID = 1L;

	public DatabaseAccessException() {}
	
	public DatabaseAccessException(String message) {
		super(message);
	}
}
