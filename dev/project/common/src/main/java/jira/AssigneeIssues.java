package jira;

import java.util.List;

import database.entity.Assignee;
import database.entity.JiraIssue;
import database.entity.converter.AssigneeConverter;

public class AssigneeIssues
{
	private final Assignee assignee;
	private List<JiraIssue> assignedJiraIssues;
	
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

	public void setAssignedJiraIssues(List<JiraIssue> assignedJiraIssues)
	{
		this.assignedJiraIssues = assignedJiraIssues;
	}

}
