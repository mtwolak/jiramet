package jira.data;

public enum ProjectData
{
	SPRING("https://jira.spring.io", "SPR", "Spring Framework", 1000),
	MONGODB("https://jira.mongodb.org", "SERVER", "MongoDB Server", 500),
	CAMUNDA("https://app.camunda.com/jira", "CAM", "Camunda BPM", 800);

	private String projectURL;
	private String projectKey;
	private String projectName;
	private int issueLimit;

	private ProjectData(String projectURL, String projectKey, String projectName, int issueLimit)
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
