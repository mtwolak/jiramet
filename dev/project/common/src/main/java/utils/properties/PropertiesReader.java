package utils.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class PropertiesReader
{
	private static final String TRUE = "true";
	private Properties properties;

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

	public int getAsInt(Property propertyToRead)
	{
		return Integer.valueOf(getAsString(propertyToRead));
	}

	public boolean getAsBoolean(Property propertyToRead)
	{
		return getAsString(propertyToRead).equalsIgnoreCase(TRUE);
	}

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
