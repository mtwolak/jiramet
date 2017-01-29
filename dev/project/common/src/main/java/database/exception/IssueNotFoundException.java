package database.exception;
/**
 * Exception for indicating problems with lacking issue in database
 */
public class IssueNotFoundException extends RuntimeException
{

	/**
	 * Constructs exception, which informs issue with given ID does not exist in database
	 * @param issueID if of issue which could not be found in database
	 */
	public IssueNotFoundException(int issueID)
	{
		super("Issue with id " + issueID + " not found in database. Make sure it exists.");
	}

	private static final long serialVersionUID = 2779719460738241674L;

}
