package utils.properties.hibernate;

import utils.properties.Property;

public class HibernateProductionConfiguration extends HibernateConfiguration
{

	@Override
	public Property getConnectionUrl()
	{
		return Property.HIBERNATE_SCHEMA_PRODUCTION;
	}

}
