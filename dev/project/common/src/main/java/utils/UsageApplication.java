package utils;

public class UsageApplication
{
	
	private String[] arguments;

	public UsageApplication(String[] arguments)
	{
		this.arguments = arguments;
	}

	public String getArg(int argNumber)
	{
		if(argNumber > arguments.length) {
			throw new InsufficientArgumentsException();
		}
		return arguments[--argNumber];
	}

	public void showUsage()
	{
		System.out.print("Usage: java -jar package.jar <Path to properties>");
	}
	

}
