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

/**
 * Database context. Contains all essential methods, mainly responsible for inserting new data to the local database.
 *
 */
public class IssueDbContext
{

	private DatabaseManager dbm;
	private static final Logger LOGGER = Logger.getLogger(IssueDbContext.class);

	/**
	 * Database context initialization. Establishes connection to the local database.
	 * 
	 * @param hibernateConfiguration database properties
	 * @see HibernateConfiguration
	 */
	public IssueDbContext(HibernateConfiguration hibernateConfiguration)
	{
		dbm = new DatabaseManager(hibernateConfiguration);
	}

	/**
	 * Adds the selected JIRA issue to the database (if not exists)
	 * 
	 * @param jiraIssue JIRA issue that is supposed to be added to the database
	 * @return ORM object referring to newly added JIRA issue or null if the issue already exists in database
	 * @see JiraIssue
	 */
	public JiraIssue addNewJiraIssue(JiraIssue jiraIssue)
	{
		if (this.getJiraIssue(jiraIssue) == null)
		{
			dbm.persist(jiraIssue);
			return jiraIssue;
		}
		return null;
	}

	/**
	 * 
	 * @param assignedIssue connection between issue and assignee that is supposed to be added to the database
	 * @return true if the data has been added to database or false if it already existed
	 * @see AssignedIssue
	 */
	public boolean addNewAssignedIssue(AssignedIssue assignedIssue)
	{
		if (this.getAssignedIssue(assignedIssue) == null)
		{
			dbm.persist(assignedIssue);
			return true;
		}
		return false;
	}
	
	/**
	 * Adds the selected JIRA project to the database (if not exists)
	 * 
	 * @param projectName name of the JIRA project that is supposed to be added to the database
	 * @return ORM object referring to newly added JIRA project or the one that already exists in database
	 * @see JiraProject
	 */
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

	/**
	 * Adds the selected issue priority to the database (if not exists)
	 * 
	 * @param priorityName name of the issue priority that is supposed to be added to the database
	 * @return ORM object referring to newly added issue priority or the one that already exists in database
	 * @see IssuePriority
	 */
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

	/**
	 * Adds the selected issue resolution to the database (if not exists)
	 * 
	 * @param resolutionName name of the issue resolution that is supposed to be added to the database
	 * @return ORM object referring to newly added issue resolution or the one that already exists in database
	 * @see IssueResolution
	 */
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

	/**
	 * Adds the selected issue type to the database (if not exists)
	 * 
	 * @param typeName name of the issue type that is supposed to be added to the database
	 * @return ORM object referring to newly added issue type or the one that already exists in database
	 * @see IssueType
	 */
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

	/**
	 * Adds the selected issue assignee to the database (if not exists)
	 * 
	 * @param assigneeName name of the issue assignee that is supposed to be added to the database
	 * @return ORM object referring to newly added issue assignee or the one that already exists in database
	 * @see Assignee
	 */
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

	/**
	 * Adds the selected issue reporter to the database (if not exists)
	 * 
	 * @param reporterName name of the issue reporter that is supposed to be added to the database
	 * @return ORM object referring to newly added issue reporter or the one that already exists in database
	 * @see IssueReporter
	 */
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

	/**
	 * Adds the selected issue comment to the database (if not exists)
	 * 
	 * @param issueComment content of the comment that is supposed to be added to the database
	 * @return true if the comment has been added to database or false if it already existed
	 * @see IssueComment
	 */
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
	private Assignee getAssignee(String assigneeName)
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

	/**
	 * 
	 * @return collection of all issue assignees stored in the database
	 * @see Assignee
	 */
	@SuppressWarnings("unchecked")
	public List<Assignee> getAllAssignees()
	{
		Session session = dbm.getSession();
		List<Assignee> assignees = session.createCriteria(Assignee.class).list();
		session.close();
		return assignees;
	}

	/**
	 * Perform the UPDATE operation on all selected issue assignees
	 * 
	 * @param assignees collection of issue assignees that are supposed to be updated
	 */
	public void updateAssignees(List<Assignee> assignees)
	{
		Session session = dbm.getSession();
		for (Assignee assignee : assignees)
		{
			dbm.persist(assignee);
		}
		session.close();
	}

	/**
	 * 
	 * @return collection of all issue reporters stored in the database
	 * @see IssueReporter
	 */
	@SuppressWarnings("unchecked")
	public List<IssueReporter> getAllReporters()
	{
		Session session = dbm.getSession();
		List<IssueReporter> reporters = session.createCriteria(IssueReporter.class).list();
		session.close();
		return reporters;
	}

	/**
	 * Perform the UPDATE operation on all selected issue reporters
	 * 
	 * @param reporters collection of issue reporters that are supposed to be updated
	 */
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
	private IssueComment getCommentIssue(String content, JiraIssue jiraIssue)
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
	private IssuePriority getIssuePriority(String priorityName)
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
	private IssueReporter getIssueReporter(String reporterName)
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
	private IssueResolution getIssueResolution(String resolutionName)
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
	private IssueType getIssueType(String typeName)
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
	
	/**
	 * 
	 * @param projectName selected JIRA project name
	 * @return ORM object referring to requested JIRA project or null if the database does not contain requested project
	 * @see JiraProject
	 */
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
	private JiraIssue getJiraIssue(JiraIssue jiraIssue)
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
	private AssignedIssue getAssignedIssue(AssignedIssue assignedIssue)
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

	/**
	 * Sets the first response date of an issue, which is equivalent to the date of the first posted comment
	 * 
	 * @param issue issue that is supposed to be updated
	 * @return issue with updated first response date
	 */
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

	/**
	 * Sets the first response date of an issue, which is equivalent to its resolve date
	 * 
	 * @param issue issue that is supposed to be updated
	 */
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

	/**
	 * Initializes the database connection
	 */
	public void initDbm()
	{
		dbm.init();
	}

}
