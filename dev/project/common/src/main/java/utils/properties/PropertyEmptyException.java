package utils.properties;

/**
 * Exception raised when property found, but with no assigned value
 *
 */
public class PropertyEmptyException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs exception with property with no assigned value
	 * @param propertyToRead property with no assigned value
	 */
	public PropertyEmptyException(Property propertyToRead)
	{
		super("Property " + propertyToRead + " found in properties file, but is has empty or null value.");
	}

}
