package utils;

/**
 * Class for showing application usage when no parameters were given during
 * execution
 *
 */
public class UsageApplication
{

	private String[] arguments;

	/**
	 * Constructs object with given program arguments
	 * 
	 * @param arguments
	 */
	public UsageApplication(String[] arguments)
	{
		this.arguments = arguments;
	}

	/**
	 * Gets argument passed to a program
	 * 
	 * @param argNumber
	 *            argument number
	 * @throws InsufficientArgumentsException
	 *             when argument does not exist
	 * @return command line argument
	 */
	public String getArg(int argNumber)
	{
		if (argNumber > arguments.length)
		{
			throw new InsufficientArgumentsException();
		}
		return arguments[--argNumber];
	}

	/**
	 * Shows application usage
	 */
	public void showUsage()
	{
		System.out.print("Usage: java -jar package.jar <Path to properties>");
	}

	/**
	 * Shows error message when issue was not found in database
	 * 
	 * @param errorMessage
	 *            error to be shown in console
	 */
	public void showIssueNotFoundText(String errorMessage)
	{
		System.out.println("Cannot predict, error message: " + errorMessage);

	}

}
