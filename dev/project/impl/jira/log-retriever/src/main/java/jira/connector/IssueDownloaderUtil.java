package jira.connector;

import java.sql.Timestamp;
import java.util.List;

import com.atlassian.jira.rest.client.api.domain.Issue;

import database.entity.*;
import jira.data.FieldPicker;
import jira.data.IssueDbContext;
import jira.json.CommentDownloader;

public class IssueDownloaderUtil
{

	public static boolean addSingleIssueToDatabase(IssueDbContext dbm, String projectName, Issue issue)
	{
		JiraProject jiraProject = dbm.getJiraProject(projectName);

		IssuePriority issuePriority = dbm.addIssuePriorityIfNotExists(FieldPicker.getPriority(issue.getPriority()));
		IssueResolution issueResolution = dbm.addIssueResolutionIfNotExists(FieldPicker.getResolution(issue.getResolution()));
		IssueStatus issueStatus = FieldPicker.getStatus(issue.getStatus().getName());
		IssueType issueType = dbm.addIssueTypeIfNotExists(FieldPicker.getType(issue.getIssueType()));
		IssueReporter issueReporter = dbm.addIssueReporterIfNotExists(FieldPicker.getReporter(issue.getReporter()));
		Assignee assignee = dbm.addAssigneeIfNotExists(FieldPicker.getAssignee(issue.getAssignee()));

		JiraIssue jiraIssue = new JiraIssue();
		jiraIssue.setJiraProject(jiraProject);
		jiraIssue.setIssueReporter(issueReporter);
		jiraIssue.setIssueType(issueType);
		jiraIssue.setIssueResolution(issueResolution);
		jiraIssue.setIssueStatus(issueStatus);
		jiraIssue.setIssuePriority(issuePriority);
		jiraIssue.setCode(issue.getKey());
		jiraIssue.setCreatedAt(new Timestamp(issue.getCreationDate().getMillis()));
		jiraIssue.setDescription(issue.getDescription());
		jiraIssue.setFirstResponseDate(FieldPicker.getFirstResponseDate(issue));

		JiraIssue addedIssue = dbm.addNewJiraIssue(jiraIssue);

		if (addedIssue != null)
		{
			AssignedIssue assignedIssue = new AssignedIssue();
			assignedIssue.setAssignee(assignee);
			assignedIssue.setJiraIssue(jiraIssue);
			assignedIssue.setResolvedAt(FieldPicker.getFirstResolveDate(issue));
			dbm.addNewAssignedIssue(assignedIssue);

			List<IssueComment> issueComments = (List<IssueComment>) CommentDownloader.loadCommentsFromIssue(addedIssue,
					projectName);

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

	public static String addProjectToDatabase(IssueDbContext dbm, String projectKey)
	{

		String projectName;

		switch (projectKey)
		{
		case "SPR":
			projectName = "Spring Framework";
			break;
		case "SERVER":
			projectName = "MongoDB Server";
			break;
		case "CAM":
			projectName = "Camunda BPM";
			break;
		default:
			projectName = "";
			break;
		}

		dbm.addProjectIfNotExists(projectName);
		return projectName;
	}

}
