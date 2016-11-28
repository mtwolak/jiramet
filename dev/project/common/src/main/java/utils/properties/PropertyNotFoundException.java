package utils.properties;

public class PropertyNotFoundException extends RuntimeException
{

	private static final long serialVersionUID = 1L;

	public PropertyNotFoundException(Property property)
	{
		super("Property " + property.name() + " not found - it should be defined in properties.txt as uppercase");
	}

	public PropertyNotFoundException()
	{
		super("Property file was not found. There is no properties.txt");
	}

}
