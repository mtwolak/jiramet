package utils.properties.hibernate;

import utils.properties.PropertiesReader;
import utils.properties.Property;

/**
 * 
 * @author Marcin
 *
 */
public class HibernateProductionConfiguration extends HibernateConfiguration
{

	/**
	 * Constructs database properties according to delivered properties reader
	 * 
	 * @param propertiesReader
	 *            property reader which holds information about database
	 */
	public HibernateProductionConfiguration(PropertiesReader propertiesReader)
	{
		super(propertiesReader);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Property getConnectionUrl()
	{
		return Property.HIBERNATE_SCHEMA_PRODUCTION;
	}

}
