package utils.properties.hibernate;

import utils.properties.PropertiesReader;
import utils.properties.Property;

public class HibernateTestConfiguration extends HibernateConfiguration
{

	public HibernateTestConfiguration(PropertiesReader propertiesReader)
	{
		super(propertiesReader);
	}

	@Override
	public Property getConnectionUrl()
	{
		return Property.HIBERNATE_SCHEMA_TEST;
	}

}
