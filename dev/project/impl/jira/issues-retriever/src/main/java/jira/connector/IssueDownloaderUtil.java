package jira.connector;

import java.sql.Timestamp;
import java.util.List;

import com.atlassian.jira.rest.client.api.domain.Issue;

import database.entity.*;
import jira.data.FieldPicker;
import jira.data.IssueDbContext;
import jira.json.CommentDownloader;
import jira.project.ProjectData;

public class IssueDownloaderUtil
{

	/**
	 * Adds selected issue to the local database. In order to be added to the database the issues 
	 * needs to fulfill two main conditions: The Description and Summary fields cannot be empty/null
	 * 
	 * @param dbm database context variable
	 * @param project essential data about the JIRA project, from which the issue have been downloaded
	 * @param issue single JIRA issue that is supposed to be added to the database
	 * @return true if the issue has been added to the database
	 * @see IssueDbContext
	 * @see ProjectData
	 */
	public static boolean addSingleIssueToDatabase(IssueDbContext dbm, ProjectData project, Issue issue)
	{

		if (issue.getDescription() == null || "".equals(issue.getDescription()) || issue.getSummary() == null
				|| "".equals(issue.getSummary()))
		{
			return false;
		}

		JiraProject jiraProject = dbm.getJiraProject(project.getProjectName());
		FieldPicker fieldPicker = new FieldPicker();

		IssuePriority issuePriority = dbm.addIssuePriorityIfNotExists(fieldPicker.getPriority(issue.getPriority()));
		IssueResolution issueResolution = dbm
				.addIssueResolutionIfNotExists(fieldPicker.getResolution(issue.getResolution()));
		IssueStatus issueStatus = fieldPicker.getStatus(issue.getStatus().getName());
		IssueType issueType = dbm.addIssueTypeIfNotExists(fieldPicker.getType(issue.getIssueType()));
		IssueReporter issueReporter = dbm.addIssueReporterIfNotExists(fieldPicker.getReporter(issue.getReporter()));
		Assignee assignee = dbm.addAssigneeIfNotExists(fieldPicker.getAssignee(issue.getAssignee()));

		JiraIssue jiraIssue = new JiraIssue();
		jiraIssue.setJiraProject(jiraProject);
		jiraIssue.setIssueReporter(issueReporter);
		jiraIssue.setIssueType(issueType);
		jiraIssue.setIssueResolution(issueResolution);
		jiraIssue.setIssueStatus(issueStatus);
		jiraIssue.setIssuePriority(issuePriority);
		jiraIssue.setSummary(issue.getSummary());
		jiraIssue.setCode(issue.getKey());
		jiraIssue.setCreatedAt(new Timestamp(issue.getCreationDate().getMillis()));
		jiraIssue.setDescription(issue.getDescription());
		jiraIssue.setFirstResponseDate(fieldPicker.getFirstResponseDate(issue));

		JiraIssue addedIssue = dbm.addNewJiraIssue(jiraIssue);

		if (addedIssue != null)
		{
			AssignedIssue assignedIssue = new AssignedIssue();
			assignedIssue.setAssignee(assignee);
			assignedIssue.setJiraIssue(jiraIssue);
			assignedIssue.setResolvedAt(fieldPicker.getFirstResolveDate(issue));
			dbm.addNewAssignedIssue(assignedIssue);

			List<IssueComment> issueComments = (List<IssueComment>) CommentDownloader.loadCommentsFromIssue(addedIssue,
					project);

			for (IssueComment ic : issueComments)
			{
				dbm.addIssueCommentIfNotExists(ic);
			}

			if (addedIssue.getFirstResponseDate() == null)
			{
				addedIssue = dbm.setFirstResponseDateByComment(addedIssue);
			}
			if (addedIssue.getFirstResponseDate() == null)
			{
				dbm.setFirstResponseDateAsResolved(addedIssue);
			}
		}

		return true;
	}

	/**
	 * Adds selected JIRA project to the local database (if not exists).
	 * 
	 * @param dbm database context variable
	 * @param project data about the JIRA project that is supposed to be added to the database
	 * @return ORM object referring to newly added or existing JIRA project
	 * @see IssueDbContext
	 * @see ProjectData
	 */
	public static JiraProject addProjectToDatabase(IssueDbContext dbm, ProjectData project)
	{
		return dbm.addProjectIfNotExists(project.getProjectName());
	}

}
