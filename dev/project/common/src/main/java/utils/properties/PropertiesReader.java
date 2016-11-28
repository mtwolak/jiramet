package utils.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader
{
	private Properties properties;
	private static final String PROPERTY_FILENAME = "../../properties.txt";

	public PropertiesReader()
	{
		this.properties = new Properties();
		loadPropertiesFromFile();
	}

	private void loadPropertiesFromFile()
	{
		try
		{
			this.properties.load(getPropertiesFromFile());
		} catch (IOException e)
		{
			throw new PropertyNotFoundException();
		}
	}

	private InputStream getPropertiesFromFile() throws IOException
	{
		return new FileInputStream(new File(PROPERTY_FILENAME));
	}

	public String get(Property propertyToRead)
	{
		String property = getProperty(propertyToRead);
		if (property == null)
		{
			throw new PropertyNotFoundException(propertyToRead);
		}
		return property;
	}

	private String getProperty(Property propertyToRead)
	{
		return propertyToRead != null ? this.properties.getProperty(propertyToRead.name()) : null;
	}

}
