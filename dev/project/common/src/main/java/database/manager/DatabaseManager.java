package database.manager;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import utils.properties.hibernate.HibernateConfiguration;

/**
 * Class responsible for state of database
 *
 */
public class DatabaseManager
{

	private SessionFactory factory;
	private final HibernateConfiguration hibernateConfiguration;
	private static final Logger LOGGER = Logger.getLogger(DatabaseManager.class);

	/**
	 * Constructs new object with given database configuration
	 * @param hibernateConfiguration database configuration
	 */
	public DatabaseManager(HibernateConfiguration hibernateConfiguration)
	{
		this.hibernateConfiguration = hibernateConfiguration;
	}

	/**
	 * Init db
	 */
	public void init()
	{
		Configuration config = hibernateConfiguration.getConfiguration();
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(config.getProperties())
				.buildServiceRegistry();
		factory = config.buildSessionFactory(serviceRegistry);
	}

	/**
	 * Persisting objects in database
	 * @param entity object to be persisted
	 */
	public void persist(Object entity)
	{
		Session session = factory.openSession();
		try
		{
			Transaction tx = session.beginTransaction();
			session.saveOrUpdate(entity);
			LOGGER.info("Persisted " + entity);
			tx.commit();
		} catch (Exception e)
		{
			LOGGER.error("Cannot save entity", e);
		} finally
		{
			session.close();
		}
	}

	/**
	 * Method for executing raw sql query - should not be used in production code
	 * @param sqlQuery sql query to be executed
	 */
	public void executeStringSql(String sqlQuery)
	{
		Session session = factory.openSession();
		try
		{
			Query query = session.createSQLQuery(sqlQuery);
			query.executeUpdate();
		} catch (Exception e)
		{
			LOGGER.error("Cannot execute string SQL" + sqlQuery, e);
		} finally
		{
			session.close();
		}
	}

	/**
	 * Getting current session opened during init
	 * @return active session
	 */
	public Session getSession()
	{
		return factory.openSession();
	}

}
