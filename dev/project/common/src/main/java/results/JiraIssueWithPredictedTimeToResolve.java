package results;

import database.entity.AssignedIssue;
import jira.AssigneeTimeResolve;

public class JiraIssueWithPredictedTimeToResolve
{

	private final AssigneeTimeResolve assigneeTimeResolve;
	private final AssignedIssue jiraIssue;

	public JiraIssueWithPredictedTimeToResolve(AssignedIssue jiraIssue, AssigneeTimeResolve assigneeTimeResolve)
	{
		this.jiraIssue = jiraIssue;
		this.assigneeTimeResolve = assigneeTimeResolve;
	}

	public AssigneeTimeResolve getAssigneeTimeResolve()
	{
		return assigneeTimeResolve;
	}

	public AssignedIssue getJiraIssue()
	{
		return jiraIssue;
	}	

}
