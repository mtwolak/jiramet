package utils.properties.hibernate;

import org.hibernate.cfg.Configuration;

import database.entity.AssignedIssue;
import database.entity.Assignee;
import database.entity.IssueComment;
import database.entity.IssuePriority;
import database.entity.IssueReporter;
import database.entity.IssueResolution;
import database.entity.IssueType;
import database.entity.JiraIssue;
import database.entity.JiraProject;
import utils.properties.PropertiesReader;
import utils.properties.Property;

/**
 * Holds configuration about database
 *
 */
public abstract class HibernateConfiguration
{
	private PropertiesReader propertiesReader;

	/**
	 * Constructs database properties according to delivered properties reader
	 * 
	 * @param propertiesReader
	 *            property reader which holds information about database
	 */
	public HibernateConfiguration(PropertiesReader propertiesReader)
	{
		this.propertiesReader = propertiesReader;
	}

	/**
	 * Gets connection url needed for communication with database
	 * @return connection url
	 */
	public abstract Property getConnectionUrl();

	/**
	 * Gets current database configuration
	 * @return current database configuration
	 */
	public Configuration getConfiguration()
	{
		Configuration configuration = new Configuration();
		setConnectionProperties(configuration);
		setEntityClassess(configuration);
		return configuration;
	}

	private void setEntityClassess(Configuration configuration)
	{
		configuration.addAnnotatedClass(AssignedIssue.class).addAnnotatedClass(IssueComment.class)
				.addAnnotatedClass(IssuePriority.class).addAnnotatedClass(IssueReporter.class)
				.addAnnotatedClass(IssueResolution.class).addAnnotatedClass(IssueType.class)
				.addAnnotatedClass(JiraIssue.class).addAnnotatedClass(JiraProject.class)
				.addAnnotatedClass(Assignee.class);
	}

	private void setConnectionProperties(Configuration configuration)
	{
		configuration.setProperty("hibernate.connection.url", propertiesReader.getAsString(getConnectionUrl()))
				.setProperty("hibernate.connection.driver_class",
						propertiesReader.getAsString(Property.HIBERNATE_DRIVER_CLASS))
				.setProperty("hibernate.connection.username", propertiesReader.getAsString(Property.HIBERNATE_USER))
				.setProperty("hibernate.connection.password", propertiesReader.getAsString(Property.HIBERNATE_PASSWORD))
				.setProperty("hibernate.dialect", propertiesReader.getAsString(Property.HIBERNATE_DIALECT));
	}
}
