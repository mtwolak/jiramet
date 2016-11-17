package jira.connector;


import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.User;

import database.entity.*;
import jira.data.IssueDbContext;

public class IssueDownloaderUtil {
	
	public static boolean addSingleIssueToDatabase(IssueDbContext dbm, String projectName, Issue issue) {

		JiraProject jiraProject = dbm.getJiraProject(projectName);
		
		IssuePriority issuePriority = dbm.addIssuePriorityIfNotExists(issue.getPriority().getName());		
		IssueResolution issueResolution = dbm.addIssueResolutionIfNotExists(issue.getResolution().getName());
		IssueStatus issueStatus = dbm.addIssueStatusIfNotExists(issue.getStatus().getName());
		IssueType issueType = dbm.addIssueTypeIfNotExists(issue.getIssueType().getName());
		IssueReporter issueReporter = dbm.addIssueReporterIfNotExists(issue.getReporter().getDisplayName());

		Assignee assignee = dbm.addAssigneeIfNotExists(getAssignee(issue.getAssignee()));
		Set<Assignee> assignees = new HashSet<Assignee>();
		assignees.add(assignee);
		
		JiraIssue jiraIssue = new JiraIssue();
		jiraIssue.setJiraProject(jiraProject);
		jiraIssue.setIssueReporter(issueReporter);
		jiraIssue.setIssueType(issueType);
		//jiraIssue.setIssueAssignees(assignees);
		jiraIssue.setIssueResolution(issueResolution);
		jiraIssue.setIssueStatus(issueStatus);
		jiraIssue.setIssuePriority(issuePriority);
		jiraIssue.setCode(issue.getKey());
		jiraIssue.setCreatedAt(new Timestamp(issue.getCreationDate().getMillis()));
		jiraIssue.setDescription(issue.getDescription());
		
		dbm.addNewJiraIssue(jiraIssue);
		
		return true;
	}

	public static String addProjectToDatabase(IssueDbContext dbm, String projectKey) {
		
		String projectName;

		switch (projectKey) {
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
	
	private static String getAssignee(User agne){
		if(agne == null){
			return "Unassigned";
		}
		return agne.getDisplayName();
	}

	private void addCommentsToDatabase(String issueKey) {

	}

}
