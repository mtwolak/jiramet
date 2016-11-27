package database.application;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import database.entity.JiraIssue;
import database.entity.JiraProject;
import database.manager.DataBaseType;
import database.manager.DatabaseManager;

public class DatabaseApplication
{
	private Session session;
	private Criteria criteria;

	public DatabaseApplication()
	{
		DatabaseManager dbm = new DatabaseManager(DataBaseType.PRODUCTION);
		dbm.init();
		session = dbm.getSession();
	}

	@SuppressWarnings("rawtypes")
	public JiraIssue getJiraIssue(int issueID)
	{
		criteria = session.createCriteria(JiraIssue.class);
		List issues = criteria.add(Restrictions.eq("id", issueID)).list();

		if (issues.size() >= 1)
		{
			return (JiraIssue) issues.get(0);
		}

		return null;
	}

	@SuppressWarnings("rawtypes")
	public JiraProject getJiraProject(int projectID)
	{
		criteria = session.createCriteria(JiraProject.class);
		List projects = criteria.add(Restrictions.eq("id", projectID)).list();

		if (projects.size() >= 1)
		{
			return (JiraProject) projects.get(0);
		}

		return null;
	}

	@SuppressWarnings("rawtypes")
	public List getJiraProjects()
	{
		criteria = session.createCriteria(JiraProject.class);
		List projects = criteria.list();

		if (projects.size() >= 1)
		{
			return projects;
		}

		return null;
	}

	@SuppressWarnings("rawtypes")
	public List getJiraIssues(JiraProject projectID)
	{
		criteria = session.createCriteria(JiraIssue.class);
		criteria.add(Restrictions.eq("jiraProject", projectID));
		List issues = criteria.list();

		if (issues.size() >= 1)
		{
			return issues;
		}

		return null;
	}

	public void closeSession()
	{
		session.close();
	}

}
