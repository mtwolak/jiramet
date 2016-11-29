package database.application;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import database.entity.JiraIssue;
import database.entity.JiraProject;
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
			criteria = session.createCriteria(JiraIssue.class);
			issues = criteria.add(Restrictions.eq("id", issueID)).list();
			if (issues.size() >= 1)
			{
				return (JiraIssue) issues.get(0);
			}
		}catch(Exception ex){
			return null;
		}
		return null;
	}

	public JiraProject getJiraProject(int projectID)
	{
		try{
			criteria = session.createCriteria(JiraProject.class);
			projects = criteria.add(Restrictions.eq("id", projectID)).list();
			if (projects.size() >= 1)
			{
				return (JiraProject) projects.get(0);
			}
		}catch(Exception ex){
			return null;
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public List getJiraProjects()
	{
		try{
			criteria = session.createCriteria(JiraProject.class);
			projects = criteria.list();
			if (projects.size() >= 1)
			{
				return projects;
			}
		}catch(Exception ex){
			return null;
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public List getJiraIssues(JiraProject projectID)
	{
		try{
			criteria = session.createCriteria(JiraIssue.class);
			criteria.add(Restrictions.eq("jiraProject", projectID));
			issues = criteria.list();
			if (issues.size() >= 1)
			{
				return issues;
			}
		}catch(Exception ex){
			return null;
		}
		return null;
	}

	public void closeSession()
	{
		session.close();
	}

}
