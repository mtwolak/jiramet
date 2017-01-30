package utils.properties;

/**
 * Exception indicating some property could not be founded in property file
 *
 */
public class PropertyNotFoundException extends RuntimeException
{

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs exception with property which could not be found in database
	 * 
	 * @param property
	 *            property which could not be found
	 */
	public PropertyNotFoundException(Property property)
	{
		super("Property " + property.name() + " not found - it should be defined in properties.txt as uppercase");
	}

	/**
	 * Raised when property file cannot be found
	 */
	public PropertyNotFoundException()
	{
		super("Property file was not found. There is no properties.txt");
	}

}
