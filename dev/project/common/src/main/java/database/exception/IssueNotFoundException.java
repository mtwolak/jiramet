package database.exception;

public class IssueNotFoundException extends RuntimeException
{

	public IssueNotFoundException(int issueID)
	{
		super("Issue with id " + issueID + " not found in database. Make sure it exists.");
	}

	private static final long serialVersionUID = 2779719460738241674L;

}
