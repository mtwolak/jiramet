package jira;

import java.util.List;

import database.entity.Assignee;
import database.entity.JiraIssue;

public class AssigneeIssues
{
	private final Assignee assignee;
	private final List<JiraIssue> assignedJiraIssues;
	
	public AssigneeIssues(Assignee assignee, List<JiraIssue> assignedJiraIssues)
	{
		this.assignee = assignee;
		this.assignedJiraIssues = assignedJiraIssues;
	}
	
	public Assignee getAssignee() {
		return assignee;
	}

	public List<JiraIssue> getAssignedJiraIssues() {
		return assignedJiraIssues;
	}

}
