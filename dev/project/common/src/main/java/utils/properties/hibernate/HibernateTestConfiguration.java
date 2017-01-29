package utils.properties.hibernate;

import utils.properties.PropertiesReader;
import utils.properties.Property;

public class HibernateTestConfiguration extends HibernateConfiguration
{
	/**
	 * Constructs database properties according to delivered properties reader
	 * 
	 * @param propertiesReader
	 *            property reader which holds information about database
	 */
	public HibernateTestConfiguration(PropertiesReader propertiesReader)
	{
		super(propertiesReader);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Property getConnectionUrl()
	{
		return Property.HIBERNATE_SCHEMA_TEST;
	}

}
