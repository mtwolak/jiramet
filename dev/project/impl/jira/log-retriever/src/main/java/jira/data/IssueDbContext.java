package jira.data;

import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.TransientObjectException;
import org.hibernate.criterion.Restrictions;

import database.entity.*;
import database.manager.DatabaseManager;
import utils.properties.hibernate.HibernateConfiguration;

public class IssueDbContext
{

	private DatabaseManager dbm;
	private static final Logger LOGGER = Logger.getLogger(IssueDbContext.class);

	public IssueDbContext(HibernateConfiguration hibernateConfiguration)
	{
		dbm = new DatabaseManager(hibernateConfiguration);
	}

	public JiraIssue addNewJiraIssue(JiraIssue jiraIssue)
	{
		if (this.getJiraIssue(jiraIssue) == null)
		{
			dbm.persist(jiraIssue);
			return jiraIssue;
		}
		return null;
	}

	public boolean addNewAssignedIssue(AssignedIssue assignedIssue)
	{
		if (this.getAssignedIssue(assignedIssue) == null)
		{
			dbm.persist(assignedIssue);
			return true;
		}
		return false;
	}

	public JiraProject addProjectIfNotExists(String projectName)
	{

		JiraProject jiraProject = new JiraProject();
		jiraProject.setProjectName(projectName);

		JiraProject res = this.getJiraProject(projectName);

		if (res == null)
		{
			dbm.persist(jiraProject);
			return jiraProject;
		}
		return res;
	}

	public IssuePriority addIssuePriorityIfNotExists(String priorityName)
	{

		IssuePriority issuePriority = new IssuePriority();
		issuePriority.setPriorityName(priorityName);

		IssuePriority res = this.getIssuePriority(priorityName);

		if (res == null)
		{
			dbm.persist(issuePriority);
			return issuePriority;
		}
		return res;
	}

	public IssueResolution addIssueResolutionIfNotExists(String resolutionName)
	{

		IssueResolution issueResulution = new IssueResolution();
		issueResulution.setResolutionName(resolutionName);

		IssueResolution res = this.getIssueResolution(resolutionName);

		if (res == null)
		{
			dbm.persist(issueResulution);
			return issueResulution;
		}
		return res;
	}

	public IssueType addIssueTypeIfNotExists(String typeName)
	{

		IssueType issueType = new IssueType();
		issueType.setTypeName(typeName);

		IssueType res = this.getIssueType(typeName);

		if (res == null)
		{
			dbm.persist(issueType);
			return issueType;
		}
		return res;
	}

	public Assignee addAssigneeIfNotExists(String assigneeName)
	{

		Assignee assignee = new Assignee();
		assignee.setName(assigneeName);

		Assignee res = this.getAssignee(assigneeName);

		if (res == null)
		{
			dbm.persist(assignee);
			return assignee;
		}
		return res;
	}

	public IssueReporter addIssueReporterIfNotExists(String reporterName)
	{

		IssueReporter issueReporter = new IssueReporter();
		issueReporter.setFullName(reporterName);

		IssueReporter res = this.getIssueReporter(reporterName);

		if (res == null)
		{
			dbm.persist(issueReporter);
			return issueReporter;
		}
		return res;
	}

	public boolean addIssueCommentIfNotExists(IssueComment issueComment)
	{
		if (this.getCommentIssue(issueComment.getContent(), issueComment.getJiraIssueNew()) == null)
		{
			dbm.persist(issueComment);
			return true;
		}
		return false;
	}

	@SuppressWarnings("rawtypes")
	public Assignee getAssignee(String assigneeName)
	{
		Session session = dbm.getSession();
		Criteria criteria = session.createCriteria(Assignee.class);
		List assignees = criteria.add(Restrictions.eq("name", assigneeName)).list();
		session.close();
		if (!assignees.isEmpty())
		{
			return (Assignee) assignees.get(0);
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Assignee> getAllAssignees()
	{
		Session session = dbm.getSession();
		List<Assignee> assignees = session.createCriteria(Assignee.class).list();
		session.close();
		return assignees;
	}

	public void updateAssignees(List<Assignee> assignees)
	{
		Session session = dbm.getSession();
		for (Assignee assignee : assignees)
		{
			dbm.persist(assignee);
		}
		session.close();
	}

	@SuppressWarnings("unchecked")
	public List<IssueReporter> getAllReporters()
	{
		Session session = dbm.getSession();
		List<IssueReporter> reporters = session.createCriteria(IssueReporter.class).list();
		session.close();
		return reporters;
	}
	
	public void updateReporters(List<IssueReporter> reporters)
	{
		Session session = dbm.getSession();
		for (IssueReporter reporter : reporters)
		{
			dbm.persist(reporter);
		}
		session.close();
	}

	@SuppressWarnings("rawtypes")
	public IssueComment getCommentIssue(String content, JiraIssue jiraIssue)
	{
		Session session = dbm.getSession();
		Criteria criteria = session.createCriteria(IssueComment.class);
		criteria.add(Restrictions.eq("content", content));
		criteria.add(Restrictions.eq("jiraIssueNew", jiraIssue));
		try
		{
			List comments = criteria.list();
			session.close();
			if (!comments.isEmpty())
			{
				return (IssueComment) comments.get(0);
			}
		} catch (TransientObjectException e)
		{
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public IssuePriority getIssuePriority(String priorityName)
	{
		Session session = dbm.getSession();
		Criteria criteria = session.createCriteria(IssuePriority.class);
		List priorities = criteria.add(Restrictions.eq("priorityName", priorityName)).list();
		session.close();
		if (!priorities.isEmpty())
		{
			return (IssuePriority) priorities.get(0);
		}

		return null;
	}

	@SuppressWarnings("rawtypes")
	public IssueReporter getIssueReporter(String reporterName)
	{
		Session session = dbm.getSession();
		Criteria criteria = session.createCriteria(IssueReporter.class);
		List reporters = criteria.add(Restrictions.eq("fullName", reporterName)).list();
		session.close();
		if (!reporters.isEmpty())
		{
			return (IssueReporter) reporters.get(0);
		}

		return null;
	}

	@SuppressWarnings("rawtypes")
	public IssueResolution getIssueResolution(String resolutionName)
	{
		Session session = dbm.getSession();
		Criteria criteria = session.createCriteria(IssueResolution.class);
		List resolutions = criteria.add(Restrictions.eq("resolutionName", resolutionName)).list();
		session.close();
		if (!resolutions.isEmpty())
		{
			return (IssueResolution) resolutions.get(0);
		}

		return null;
	}

	@SuppressWarnings("rawtypes")
	public IssueType getIssueType(String typeName)
	{
		Session session = dbm.getSession();
		Criteria criteria = session.createCriteria(IssueType.class);
		List types = criteria.add(Restrictions.eq("typeName", typeName)).list();
		session.close();
		if (!types.isEmpty())
		{
			return (IssueType) types.get(0);
		}

		return null;
	}

	@SuppressWarnings("rawtypes")
	public JiraIssue getJiraIssue(String code, JiraProject project)
	{
		Session session = dbm.getSession();
		Criteria criteria = session.createCriteria(JiraIssue.class);
		criteria.add(Restrictions.eq("code", code));
		criteria.add(Restrictions.eq("jiraProject", project));
		List issues = criteria.list();
		session.close();
		if (!issues.isEmpty())
		{
			return (JiraIssue) issues.get(0);
		}

		return null;
	}

	@SuppressWarnings("rawtypes")
	public JiraProject getJiraProject(String projectName)
	{
		Session session = dbm.getSession();
		Criteria criteria = session.createCriteria(JiraProject.class);
		List projects = criteria.add(Restrictions.eq("projectName", projectName)).list();
		session.close();
		if (!projects.isEmpty())
		{
			return (JiraProject) projects.get(0);
		}

		return null;
	}

	@SuppressWarnings("rawtypes")
	public JiraIssue getJiraIssue(JiraIssue jiraIssue)
	{
		Session session = dbm.getSession();
		Criteria criteria = session.createCriteria(JiraIssue.class);
		List issues = criteria.add(Restrictions.eq("code", jiraIssue.getCode())).list();
		session.close();
		if (!issues.isEmpty())
		{
			return (JiraIssue) issues.get(0);
		}

		return null;
	}

	@SuppressWarnings("rawtypes")
	public AssignedIssue getAssignedIssue(AssignedIssue assignedIssue)
	{
		Session session = dbm.getSession();
		Criteria criteria = session.createCriteria(AssignedIssue.class);
		criteria.add(Restrictions.eq("assignee", assignedIssue.getAssignee()));
		criteria.add(Restrictions.eq("jiraIssue", assignedIssue.getJiraIssue()));
		try
		{
			List assignedIssues = criteria.list();
			session.close();
			if (!assignedIssues.isEmpty())
			{
				return (AssignedIssue) assignedIssues.get(0);
			}
		} catch (TransientObjectException e)
		{
			LOGGER.error("Cannot save AssignedIssue object", e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public JiraIssue setFirstResponseDateByComment(JiraIssue issue)
	{
		Session session = dbm.getSession();
		Criteria criteria = session.createCriteria(IssueComment.class);
		criteria.add(Restrictions.eq("jiraIssueNew", issue));
		try
		{
			List<IssueComment> result = criteria.list();
			session.close();
			if (!result.isEmpty())
			{
				Timestamp minDate = result.stream().map(u -> u.getAddedAt()).min(Timestamp::compareTo).get();
				issue.setFirstResponseDate(minDate);
				dbm.persist(issue);
			}
		} catch (TransientObjectException e)
		{
			LOGGER.error("Cannot update JiraIssue object", e);
		}
		return issue;
	}

	@SuppressWarnings("unchecked")
	public void setFirstResponseDateAsResolved(JiraIssue issue)
	{
		Session session = dbm.getSession();
		Criteria criteria = session.createCriteria(AssignedIssue.class);
		criteria.add(Restrictions.eq("jiraIssue", issue));
		try
		{
			List<AssignedIssue> result = criteria.list();
			session.close();
			if (!result.isEmpty())
			{
				Timestamp minDate = result.get(0).getResolvedAt();
				issue.setFirstResponseDate(minDate);
				dbm.persist(issue);
			}
		} catch (TransientObjectException e)
		{
			LOGGER.error("Cannot update JiraIssue object", e);
		}
	}

	public void initDbm()
	{
		dbm.init();
	}

}
