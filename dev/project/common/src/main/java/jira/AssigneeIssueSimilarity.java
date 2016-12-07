package jira;

import java.util.List;

import database.entity.Assignee;
import database.entity.JiraIssue;

public class AssigneeIssueSimilarity
{
	private Assignee assignee;
	private List<JiraIssueSimilarity> jiraIssueSimilarities;
	
	public AssigneeIssueSimilarity(Assignee assignee, List<JiraIssueSimilarity> jiraIssueSimilarities)
	{
		this.assignee = assignee;
		this.jiraIssueSimilarities = jiraIssueSimilarities;
	}
	
	public Assignee getAssignee() {
		return assignee;
	}

	public void setAssignee(Assignee assignee) {
		this.assignee = assignee;
	}
	
	public List<JiraIssueSimilarity> getAssignedJiraIssues() {
		return jiraIssueSimilarities;
	}
	
	public void setAssigneedIssues(List<JiraIssueSimilarity> jiraIssueSimilarities) {
		this.jiraIssueSimilarities = jiraIssueSimilarities;
	}
}
