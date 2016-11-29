package utils.properties.hibernate;

import utils.properties.PropertiesReader;
import utils.properties.Property;

public class HibernateProductionConfiguration extends HibernateConfiguration
{

	public HibernateProductionConfiguration(PropertiesReader propertiesReader)
	{
		super(propertiesReader);
	}

	@Override
	public Property getConnectionUrl()
	{
		return Property.HIBERNATE_SCHEMA_PRODUCTION;
	}

}
