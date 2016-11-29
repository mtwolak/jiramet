package utils.properties;

public class PropertyEmptyException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public PropertyEmptyException(Property propertyToRead)
	{
		super("Property " + propertyToRead + " found in properties file, but is has empty or null value.");
	}


}
