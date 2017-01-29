package database.exception;
/**
 * Class for indicating problems with database
 *
 */
public class DatabaseAccessException extends Exception {

	private static final long serialVersionUID = 1L;

	public DatabaseAccessException() {}
	
	/**
	 * Constructs exception with given message
	 * @param message exception cause
	 */
	public DatabaseAccessException(String message) {
		super(message);
	}
}
