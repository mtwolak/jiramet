package database.entity.dto;

import java.util.Set;

public class IssueResolutionDTO
{
	private int issueResoultionId;

	private String resolutionName;

	private Set<JiraIssueDTO> jiraIssue;

	public int getIssueResoultionId()
	{
		return issueResoultionId;
	}

	public void setIssueResoultionId(int issueResoultionId)
	{
		this.issueResoultionId = issueResoultionId;
	}

	public String getResolutionName()
	{
		return resolutionName;
	}

	public void setResolutionName(String resolutionName)
	{
		this.resolutionName = resolutionName;
	}

	public Set<JiraIssueDTO> getJiraIssue()
	{
		return jiraIssue;
	}

	public void setJiraIssue(Set<JiraIssueDTO> jiraIssue)
	{
		this.jiraIssue = jiraIssue;
	}
	
	
}
