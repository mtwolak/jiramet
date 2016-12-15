package database.entity.dto;

import java.util.Set;

public class JiraProjectDTO
{
	private int jiraProjectId;

	private String projectName;

	private Set<JiraIssueDTO> jiraIssues;

	public int getJiraProjectId()
	{
		return jiraProjectId;
	}

	public void setJiraProjectId(int jiraProjectId)
	{
		this.jiraProjectId = jiraProjectId;
	}

	public String getProjectName()
	{
		return projectName;
	}

	public void setProjectName(String projectName)
	{
		this.projectName = projectName;
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
