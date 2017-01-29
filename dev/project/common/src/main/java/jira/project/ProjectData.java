package jira.project;

/**
 * Holds project for executing predictions model
 */
public class ProjectData
{
	private final String projectURL;
	private final String projectKey;
	private final String projectName;
	private final int issueLimit;

	/**
	 * Project data
	 * 
	 * @param projectURL
	 *            URL of the project
	 * @param projectKey can be found on project site
	 * @param projectName
	 *            project name
	 * @param issueLimit
	 *            limit of issues which would be retrieved during one method
	 *            invocation (bypass for retrieving all issues from projects)
	 */
	public ProjectData(String projectURL, String projectKey, String projectName, int issueLimit)
	{
		this.projectURL = projectURL;
		this.projectKey = projectKey;
		this.projectName = projectName;
		this.issueLimit = issueLimit;
	}

	/**
	 * Gets project URL
	 * @return project URL
	 */
	public String getProjectURL()
	{
		return projectURL;
	}

	/**
	 * Gets project key
	 * @return Project key
	 */
	public String getProjectKey()
	{
		return projectKey;
	}

	/**
	 * Gets project name
	 * @return project name
	 */
	public String getProjectName()
	{
		return projectName;
	}

	/**
	 * Gets issue limit per one method invocation
	 * @return issue limit
	 */
	public int getIssueLimitPerCall()
	{
		return issueLimit;
	}

}
