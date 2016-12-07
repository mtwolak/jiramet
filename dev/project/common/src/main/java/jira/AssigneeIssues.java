package jira;

import java.util.List;

import database.entity.Assignee;
import database.entity.JiraIssue;

public class AssigneeIssues
{
	private Assignee assignee;
	private List<JiraIssue> assignedJiraIssues;
	
	public AssigneeIssues(Assignee assignee, List<JiraIssue> assignedJiraIssues)
	{
		this.assignee = assignee;
		this.assignedJiraIssues = assignedJiraIssues;
	}
	
	public Assignee getAssignee() {
		return assignee;
	}

	public void setAssignee(Assignee assignee) {
		this.assignee = assignee;
	}
	
	public List<JiraIssue> getAssignedJiraIssues() {
		return assignedJiraIssues;
	}
	
	public void setAssigneedIssues(List<JiraIssue> assignedJiraIssues) {
		this.assignedJiraIssues = assignedJiraIssues;
	}
	
}
