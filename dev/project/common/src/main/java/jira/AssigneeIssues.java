package jira;

import java.util.List;

import database.entity.Assignee;
import database.entity.JiraIssue;
import database.entity.converter.AssigneeConverter;

public class AssigneeIssues
{
	private final Assignee assignee;
	private final List<JiraIssue> assignedJiraIssues;
	
	public AssigneeIssues(Assignee assignee)
	{
		this.assignee = assignee;
		this.assignedJiraIssues = AssigneeConverter.getAssignedJiraIssues(assignee);
	}
	
	public Assignee getAssignee() {
		return assignee;
	}

	public List<JiraIssue> getAssignedJiraIssues() {
		return assignedJiraIssues;
	}

}
