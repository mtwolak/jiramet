package jira.connector;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.User;

import database.entity.*;
import jira.data.IssueDbContext;

public class IssueDownloaderUtil {
	
	public static boolean addSingleIssueToDatabase(IssueDbContext dbm, String projectName, Issue issue) {

		JiraProject jiraProject = new JiraProject();
		jiraProject.setProjectName(projectName);
		
		IssuePriority issuePriority = dbm.addIssuePriorityIfNotExists(issue.getPriority().getName());		
		IssueResolution issueResolution = dbm.addIssueResolutionIfNotExists(issue.getResolution().getName());
		IssueStatus issueStatus = dbm.addIssueStatusIfNotExists(issue.getStatus().getName());
		IssueType issueType = dbm.addIssueTypeIfNotExists(issue.getIssueType().getName());
		IssueReporter issueReporter = dbm.addIssueReporterIfNotExists(issue.getReporter().getDisplayName());

		Assignee assignee = dbm.addAssigneeIfNotExists(getAssignee(issue.getAssignee()));
		Set<Assignee> assignees = new HashSet<Assignee>();
		assignees.add(assignee);
		
		JiraIssue jiraIssue = new JiraIssue();
//		jiraIssue.setJiraProject(jiraProject);
//		jiraIssue.setReporter(issueReporter);
//		jiraIssue.setIssueType(issueType);
//		jiraIssue.setAssignees(assignees);
//		jiraIssue.setResolution(issueResolution);
//		jiraIssue.setStatus(issueStatus);
//		jiraIssue.setPriority(issuePriority);
		jiraIssue.setCode(issue.getKey());
		jiraIssue.setDescription(issue.getDescription());
		
		dbm.addNewJiraIssue(jiraIssue);

//		Date startDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(issue.getCreationDate().toString());
//		jiraIssue.setCreatedAt((Timestamp)startDate);
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
