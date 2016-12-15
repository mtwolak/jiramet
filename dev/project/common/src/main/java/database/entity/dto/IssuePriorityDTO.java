package database.entity.dto;

import java.util.Set;

public class IssuePriorityDTO
{
	private int issuePriorityId;

	private String priorityName;

	private Set<JiraIssueDTO> jiraIssues;

	public int getIssuePriorityId()
	{
		return issuePriorityId;
	}

	public void setIssuePriorityId(int issuePriorityId)
	{
		this.issuePriorityId = issuePriorityId;
	}

	public String getPriorityName()
	{
		return priorityName;
	}

	public void setPriorityName(String priorityName)
	{
		this.priorityName = priorityName;
	}

	public Set<JiraIssueDTO> getJiraIssues()
	{
		return jiraIssues;
	}

	public void setJiraIssues(Set<JiraIssueDTO> jiraIssues)
	{
		this.jiraIssues = jiraIssues;
	}

}
