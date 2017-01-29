package jira;

import java.util.List;

import database.entity.Assignee;
import database.entity.JiraIssue;
import database.entity.converter.AssigneeConverter;

/**
 * Assignee and jis assigned jira issues
 *
 */
public class AssigneeIssues
{
	private final Assignee assignee;
	private List<JiraIssue> assignedJiraIssues;
	
	/**
	 * Constructs object with assignee and his jira issues
	 * @param assignee jira assignee
	 */
	public AssigneeIssues(Assignee assignee)
	{
		this.assignee = assignee;
		this.assignedJiraIssues = AssigneeConverter.getAssignedJiraIssues(assignee);
	}
	
	/**
	 * Gets jira assignee
	 * @return jira assignee
	 */
	public Assignee getAssignee() {
		return assignee;
	}

	/**
	 * Gets list of assigned jira issues to assignee
	 * @return assigned jira issues
	 */
	public List<JiraIssue> getAssignedJiraIssues() {
		return assignedJiraIssues;
	}

	/**
	 * Sets assigned jira issues
	 * @param assignedJiraIssues assigned jira issues to assignee
	 */
	public void setAssignedJiraIssues(List<JiraIssue> assignedJiraIssues)
	{
		this.assignedJiraIssues = assignedJiraIssues;
	}

}
