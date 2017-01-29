package results;

import database.entity.AssignedIssue;
import jira.AssigneeTimeResolve;

/**
 * Holds information about jira issue and its predicted time to resolve
 *
 */
public class JiraIssueWithPredictedTimeToResolve
{

	private final AssigneeTimeResolve assigneeTimeResolve;
	private final AssignedIssue jiraIssue;

	/**
	 * Constructs object with jira issues and predicted time resolve
	 * 
	 * @param jiraIssue
	 *            jira issue
	 * @param assigneeTimeResolve
	 *            predicted time to resolve issue
	 */
	public JiraIssueWithPredictedTimeToResolve(AssignedIssue jiraIssue, AssigneeTimeResolve assigneeTimeResolve)
	{
		this.jiraIssue = jiraIssue;
		this.assigneeTimeResolve = assigneeTimeResolve;
	}

	/**
	 * Gets predicted time to resolve
	 * 
	 * @return assignee time to resolve
	 */
	public AssigneeTimeResolve getAssigneeTimeResolve()
	{
		return assigneeTimeResolve;
	}

	/**
	 * Gets jira issue
	 * @return jira issue
	 */
	public AssignedIssue getJiraIssue()
	{
		return jiraIssue;
	}

}
