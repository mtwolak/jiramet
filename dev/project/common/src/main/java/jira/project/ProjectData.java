package jira.project;

public class ProjectData
{
	private final String projectURL;
	private final String projectKey;
	private final String projectName;
	private final int issueLimit;

	public ProjectData(String projectURL, String projectKey, String projectName, int issueLimit)
	{
		this.projectURL = projectURL;
		this.projectKey = projectKey;
		this.projectName = projectName;
		this.issueLimit = issueLimit;
	}

	public String getProjectURL()
	{
		return projectURL;
	}

	public String getProjectKey()
	{
		return projectKey;
	}

	public String getProjectName()
	{
		return projectName;
	}

	public int getIssueLimitPerCall()
	{
		return issueLimit;
	}

}
