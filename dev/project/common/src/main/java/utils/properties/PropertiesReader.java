package utils.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Class for reading properties from file
 *
 */
public class PropertiesReader
{
	private static final String TRUE = "true";
	private Properties properties;

	/**
	 * Constructs object and loads properties from file
	 * @param propertyPath path to property file
	 */
	public PropertiesReader(String propertyPath)
	{
		this.properties = getProperties();
		loadPropertiesFromFile(propertyPath);
	}

	protected Properties getProperties()
	{
		return new Properties();
	}

	protected void loadPropertiesFromFile(String propertyPath)
	{
		try
		{
			this.properties.load(getPropertiesFromFile(propertyPath));
		} catch (IOException e)
		{
			throw new PropertyNotFoundException();
		}
	}

	private InputStream getPropertiesFromFile(String propertyPath) throws IOException
	{
		return new FileInputStream(new File(propertyPath));
	}

	/**
	 * Gets specified property and return string as a result
	 * @param propertyToRead property to be read
	 * @return read property
	 */
	public String getAsString(Property propertyToRead)
	{
		String property = getProperty(propertyToRead);
		checkForPropertyCorrection(propertyToRead, property);
		return property;
	}
	
	private void checkForPropertyCorrection(Property propertyToRead, String property)
	{
		if (property == null)
		{
			throw new PropertyNotFoundException(propertyToRead);
		}
		if (property.equals(""))
		{
			throw new PropertyEmptyException(propertyToRead);
		}
	}

	/**
	 * Gets property as double
	 * @param property property to be read
	 * @return read property
	 */
	public double getAsDouble(Property property)
	{
		return Double.valueOf(getAsString(property));
	}

	private String getProperty(Property propertyToRead)
	{
		return propertyToRead != null ? getPropertyToHash(propertyToRead) : null;
	}

	private String getPropertyToHash(Property propertyToRead)
	{
		String stringToReturn = this.properties.getProperty(propertyToRead.name());
		return stringToReturn.contains("#") ? stringToReturn.substring(0, stringToReturn.indexOf('#')).trim() : stringToReturn.trim();
	}

	/**
	 * Gets property as int
	 * @param propertyToRead property to be read
	 * @return property as int
	 */
	public int getAsInt(Property propertyToRead)
	{
		return Integer.valueOf(getAsString(propertyToRead));
	}

	/**
	 * Gets property as boolean
	 * @param propertyToRead property to be read
	 * @return property as boolean
	 */
	public boolean getAsBoolean(Property propertyToRead)
	{
		return getAsString(propertyToRead).equalsIgnoreCase(TRUE);
	}

	/**
	 * Gets list of properties. Property entry must be separated by comma
	 * @param propertyToRead property to be read
	 * @return list of read property
	 */
	public List<String> getAsStringList(Property propertyToRead)
	{
		String loadedString = getProperty(propertyToRead);
		return isEmptyOrNull(loadedString) ? new ArrayList<>() : Arrays.asList(loadedString.split(","));
	}

	private boolean isEmptyOrNull(String stringToCheck)
	{
		return stringToCheck == null || "".equals(stringToCheck);
	}

}
