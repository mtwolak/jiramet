package database.application;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import database.entity.AssignedIssue;
import database.entity.Assignee;
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

	@SuppressWarnings("rawtypes") // mathew, do poprawy
	public AssignedIssue getAssignedIssue(int assignedIssueID)
	{
		criteria = session.createCriteria(AssignedIssue.class);
		List assignedIssues = criteria.add(Restrictions.eq("id", assignedIssueID)).list();

		if (assignedIssues.size() >= 1)
		{
			return (AssignedIssue) assignedIssues.get(0);
		}

		return null;
	}

	@SuppressWarnings("rawtypes") // mathew, do poprawy
	public Assignee getAssignee(int assigneeID)
	{
		criteria = session.createCriteria(Assignee.class);
		List assigneeList = criteria.add(Restrictions.eq("id", assigneeID)).list();

		if (assigneeList.size() >= 1)
		{
			return (Assignee) assigneeList.get(0);
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

	@SuppressWarnings("rawtypes") // mathew, do poprawy
	public List getAssignedIssues(JiraProject projectID)
	{
		criteria = session.createCriteria(AssignedIssue.class);
		criteria.add(Restrictions.eq("jiraProject", projectID));
		List assignedIssues = criteria.list();

		if (assignedIssues.size() >= 1)
		{
			return assignedIssues;
		}

		return null;
	}

	@SuppressWarnings("rawtypes") // mathew, do poprawy
	public List getAssigneeList(JiraProject projectID)
	{
		criteria = session.createCriteria(Assignee.class);
		criteria.add(Restrictions.eq("jiraProject", projectID));
		List assigneeList = criteria.list();

		if (assigneeList.size() >= 1)
		{
			return assigneeList;
		}

		return null;
	}

	public void closeSession()
	{
		session.close();
	}

}
