package database.entity.dto;

import java.sql.Timestamp;

public class AssignedIssueDTO
{
	private int assignedIssueId;
	private JiraIssueDTO jiraIssue;
	private AssigneeDTO assignee;
	private Timestamp resolvedAt = null;

	public int getAssignedIssueId()
	{
		return assignedIssueId;
	}

	public void setAssignedIssueId(int assignedIssueId)
	{
		this.assignedIssueId = assignedIssueId;
	}

	public JiraIssueDTO getJiraIssue()
	{
		return jiraIssue;
	}

	public void setJiraIssue(JiraIssueDTO jiraIssue)
	{
		this.jiraIssue = jiraIssue;
	}

	public AssigneeDTO getAssignee()
	{
		return assignee;
	}

	public void setAssignee(AssigneeDTO assignee)
	{
		this.assignee = assignee;
	}

	public Timestamp getResolvedAt()
	{
		return resolvedAt;
	}

	public void setResolvedAt(Timestamp resolvedAt)
	{
		this.resolvedAt = (Timestamp) resolvedAt.clone();
	}

}
