package results;

import database.entity.JiraIssue;
import jira.AssigneeTimeResolve;

public class JiraIssueWithPredictedTimeToResolve
{

	private final AssigneeTimeResolve assigneeTimeResolve;
	private final JiraIssue jiraIssue;

	public JiraIssueWithPredictedTimeToResolve(JiraIssue jiraIssue, AssigneeTimeResolve assigneeTimeResolve)
	{
		this.jiraIssue = jiraIssue;
		this.assigneeTimeResolve = assigneeTimeResolve;
	}

	public AssigneeTimeResolve getAssigneeTimeResolve()
	{
		return assigneeTimeResolve;
	}

	public JiraIssue getJiraIssue()
	{
		return jiraIssue;
	}

}
