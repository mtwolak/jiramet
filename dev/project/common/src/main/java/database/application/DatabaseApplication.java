package database.application;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import database.entity.JiraIssue;
import database.entity.JiraProject;
import database.exceptions.DatabaseAccessException;
import database.manager.DatabaseManager;
import utils.properties.PropertiesReader;
import utils.properties.hibernate.HibernateProductionConfiguration;

public class DatabaseApplication
{
	private Session session;
	private Criteria criteria;
	@SuppressWarnings("rawtypes")
	private List issues;
	@SuppressWarnings("rawtypes")
	private List projects;
	private Logger logger;
	
	public DatabaseApplication(PropertiesReader propertiesReader)
	{
		DatabaseManager dbm = new DatabaseManager(new HibernateProductionConfiguration(propertiesReader));
		dbm.init();
		session = dbm.getSession();
	}
	
	public DatabaseApplication(SessionFactory sessionFactory)
	{
		session = sessionFactory.getCurrentSession();
	}

	public JiraIssue getJiraIssue(int issueID)
	{
		try{
			logger = Logger.getLogger(DatabaseApplication.class.getName());
			criteria = session.createCriteria(JiraIssue.class);
			issues = criteria.add(Restrictions.eq("id", issueID)).list();
			if (issues.size() >= 1)
				return (JiraIssue) issues.get(0);
			else
				throw new DatabaseAccessException();
		}catch(DatabaseAccessException ex){
			logger.error(ex);
		}
		return null;
	}

	public JiraProject getJiraProject(int projectID)
	{
		try{
			logger = Logger.getLogger(DatabaseApplication.class.getName());
			criteria = session.createCriteria(JiraProject.class);
			projects = criteria.add(Restrictions.eq("id", projectID)).list();
			if (projects.size() >= 1)
				return (JiraProject) projects.get(0);
			else
				throw new DatabaseAccessException();
		}catch(DatabaseAccessException ex){
			logger.error(ex);
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public List getJiraProjects()
	{
		try{
			logger = Logger.getLogger(DatabaseApplication.class.getName());
			criteria = session.createCriteria(JiraProject.class);
			projects = criteria.list();
			if (projects.size() >= 1)
				return projects;
			else
				throw new DatabaseAccessException();
		}catch(DatabaseAccessException ex){
			logger.error(ex);
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public List getJiraIssues(JiraProject projectID)
	{
		try{
			logger = Logger.getLogger(DatabaseApplication.class.getName());
			criteria = session.createCriteria(JiraIssue.class);
			criteria.add(Restrictions.eq("jiraProject", projectID));
			issues = criteria.list();
			if (issues.size() >= 1)
				return issues;
			else
				throw new DatabaseAccessException();
		}catch(DatabaseAccessException ex){
			logger.error(ex);
		}
		return null;
	}

	public void closeSession()
	{
		session.close();
	}

}
