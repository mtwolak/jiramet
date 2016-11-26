package jira.connector;

import java.sql.Timestamp;
import java.util.List;

import com.atlassian.jira.rest.client.api.domain.Issue;

import database.entity.*;
import jira.data.FieldPicker;
import jira.data.IssueDbContext;
import jira.data.ProjectData;
import jira.json.CommentDownloader;

public class IssueDownloaderUtil
{

	public static boolean addSingleIssueToDatabase(IssueDbContext dbm, ProjectData project, Issue issue)
	{

		JiraProject jiraProject = dbm.getJiraProject(project.getProjectName());
		FieldPicker fieldPicker = new FieldPicker();

		IssuePriority issuePriority = dbm.addIssuePriorityIfNotExists(fieldPicker.getPriority(issue.getPriority()));
		IssueResolution issueResolution = dbm.addIssueResolutionIfNotExists(fieldPicker.getResolution(issue.getResolution()));
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

	public static JiraProject addProjectToDatabase(IssueDbContext dbm, ProjectData project)
	{
		return dbm.addProjectIfNotExists(project.getProjectName());
	}

}
