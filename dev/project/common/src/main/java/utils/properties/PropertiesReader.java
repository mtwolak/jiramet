package utils.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader
{
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
		if (property == null)
		{
			throw new PropertyNotFoundException(propertyToRead);
		}
		return property;
	}

	public double getAsDouble(Property property)
	{
		return Double.valueOf(getAsString(property));
	}

	private String getProperty(Property propertyToRead)
	{
		return propertyToRead != null ? this.properties.getProperty(propertyToRead.name()) : null;
	}

}
