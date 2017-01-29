package database.application;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import database.entity.Assignee;
import database.entity.JiraIssue;
import database.entity.JiraProject;
import database.exception.DatabaseAccessException;
import database.exception.IssueNotFoundException;
import database.manager.DatabaseManager;
import utils.properties.PropertiesReader;
import utils.properties.hibernate.HibernateProductionConfiguration;
/**
 * Class for retrieving data from database
 *
 */
public class DatabaseApplication
{
	private Session session;
	private Criteria criteria;
	@SuppressWarnings("rawtypes")
	private List issues;
	@SuppressWarnings("rawtypes")
	private List projects;
	private Logger logger = Logger.getLogger(DatabaseApplication.class.getName());

	/**
	 * Constructs new object with connection to database
	 * @param propertiesReader property reader
	 */
	public DatabaseApplication(PropertiesReader propertiesReader)
	{
		DatabaseManager dbm = new DatabaseManager(new HibernateProductionConfiguration(propertiesReader));
		dbm.init();
		session = dbm.getSession();
	}

	/**
	 * Constructs new object with given session factory
	 * @param sessionFactory session factory
	 */
	public DatabaseApplication(SessionFactory sessionFactory)
	{
		session = sessionFactory.getCurrentSession();
	}

	/**
	 * Retrieving jira issue with given issue id
	 * @param issueID issue id
	 * @throws IssueNotFoundException when no issue with given id
	 * @return Jira issue with given id. 
	 */
	public JiraIssue getJiraIssue(int issueID)
	{
		criteria = session.createCriteria(JiraIssue.class);
		issues = criteria.add(Restrictions.eq("id", issueID)).list();
		if (!issues.isEmpty())
		{
			return (JiraIssue) issues.get(0);
		} else
		{
			throw new IssueNotFoundException(issueID);
		}
	}

	/**
	 * Retrieving jira project with given ID
	 * @param projectID project ID
	 * @return JiraProject with given id. If no issue found, null is returned
	 */
	public JiraProject getJiraProject(int projectID)
	{
		try
		{
			criteria = session.createCriteria(JiraProject.class);
			projects = criteria.add(Restrictions.eq("id", projectID)).list();
			if (projects.size() >= 1)
				return (JiraProject) projects.get(0);
			else
				throw new DatabaseAccessException();
		} catch (DatabaseAccessException ex)
		{
			logger.error(ex);
		}
		return null;
	}
	
	/**
	 * Retrieving jira project by name
	 * @param projectName - jira project name
	 * @return Jira project with given project name. Null, when project not found.
	 */
	public JiraProject getJiraProject(String projectName)
	{
		try
		{
			criteria = session.createCriteria(JiraProject.class);
			projects = criteria.add(Restrictions.eq("projectName", projectName)).list();
			if (projects.size() >= 1)
				return (JiraProject) projects.get(0);
			else
				throw new DatabaseAccessException();
		} catch (DatabaseAccessException ex)
		{
			logger.error(ex);
		}
		return null;
	}

	/**
	 * Getting all jira project from database
	 * @return all jira projects from database
	 */
	@SuppressWarnings("rawtypes")
	public List getJiraProjects()
	{
		try
		{
			criteria = session.createCriteria(JiraProject.class);
			projects = criteria.list();
			if (projects.size() >= 1)
				return projects;
			else
				throw new DatabaseAccessException();
		} catch (DatabaseAccessException ex)
		{
			logger.error(ex);
		}
		return null;
	}

	/**
	 * Getting jira issues for project with given id
	 * @param projectID project id
	 * @return List of jira issues for project with given ID
	 */
	@SuppressWarnings("rawtypes")
	public List getJiraIssues(JiraProject projectID)
	{
		try
		{
			criteria = session.createCriteria(JiraIssue.class);
			criteria.add(Restrictions.eq("jiraProject", projectID));
			issues = criteria.list();
			if (issues.size() >= 1)
				return issues;
			else
				throw new DatabaseAccessException();
		} catch (DatabaseAccessException ex)
		{
			logger.error(ex);
		}
		return null;
	}

	/**
	 * Gets Jira issues for project with given ID ordered descending by creation date
	 * @param projectID project id
	 * @return list of ordered jira issues. Null, when no jira issues
	 */
	@SuppressWarnings("rawtypes")
	public List getJiraIssuesOrderedByCreationDate(JiraProject projectID)
	{
		try
		{
			criteria = session.createCriteria(JiraIssue.class);
			criteria.add(Restrictions.eq("jiraProject", projectID));
			criteria.addOrder(Order.desc("createdAt"));
			issues = criteria.list();
			if (issues.size() >= 1)
				return issues;
			else
				throw new DatabaseAccessException();
		} catch (DatabaseAccessException ex)
		{
			logger.error(ex);
		}
		return null;
	}
	
	/**
	 * Gets given percent of whole issues from the given project
	 * @param projectID project id with issues
	 * @param percentageScope percent of how many issues will be returned
	 * @return jira issues
	 */
	public List<JiraIssue> getPercentageScopeOfJiraIssues(JiraProject projectID, int percentageScope)
	{
		issues = getJiraIssuesOrderedByCreationDate(projectID);
		List<JiraIssue> percentageOfIssues = new ArrayList<JiraIssue>();
		int numberOfIssuesInScope = (int) (issues.size() * ((double) percentageScope / 100));
		for(int i=0; i<numberOfIssuesInScope; i++)
		{
			percentageOfIssues.add((JiraIssue) issues.get(i));
		}
		return percentageOfIssues;
	}

	/**
	 * Gets list of all jira assignees, skipping unassigned.
	 * @param jiraProject jira project
	 * @return Jira assignees
	 */
	@SuppressWarnings("rawtypes")
	public List getJiraAssignees(JiraProject jiraProject)
	{
		try
		{
			criteria = session.createCriteria(Assignee.class);
			criteria.add(Restrictions.ne("name", "Unassigned"));
			List assignees = criteria.list();
			if (assignees.size() >= 1)
				return assignees;
			else
				throw new DatabaseAccessException();
		} catch (DatabaseAccessException ex)
		{
			logger.error(ex);
		}
		return null;
	}

	/**
	 * Closing opened session
	 */
	public void closeSession()
	{
		session.close();
	}

}
