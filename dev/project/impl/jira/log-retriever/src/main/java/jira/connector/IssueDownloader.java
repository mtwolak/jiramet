package jira.connector;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.util.concurrent.Promise;

import database.manager.DataBaseType;
import jira.data.IssueDbContext;

public class IssueDownloader
{

	private JiraUtil jiraUtil;
	private IssueDbContext idc;

	public IssueDownloader(DataBaseType dataBaseType)
	{
		jiraUtil = new JiraUtil();
		idc = new IssueDbContext(dataBaseType);
	}

	public void retriveAllIssues()
	{
		idc.initDbm();
		addIssuesFromCamundaProject();
		addIssuesFromMongoDBProject();
		addIssuesFromSpringProject();
		idc.closeDbm();
	}

	private void addIssuesToDatabase(Promise<SearchResult> searchRes, String projectKey)
	{
		String projectName = IssueDownloaderUtil.addProjectToDatabase(idc, projectKey);

		if (projectName != "")
		{
			for (Issue issue : searchRes.claim().getIssues())
			{
				IssueDownloaderUtil.addSingleIssueToDatabase(idc, projectName, issue);
			}
		}
	}

	public void addIssuesFromSpringProject()
	{
		int startAt = 0;
		int totalResults = jiraUtil.getTotalIssueCountFromSpringProject();

		while (startAt < totalResults)
		{
			Promise<SearchResult> springRes = jiraUtil.getIssuesFromSpringProject(startAt, 1000);
			addIssuesFromProject(springRes, JiraUtil.JIRA_SPRING_FRAMEWORK_PROJECTKEY);
			startAt += 1000;
		}
	}

	public void addIssuesFromMongoDBProject()
	{
		int startAt = 0;
		int totalResults = jiraUtil.getTotalIssueCountFromMongoDBProject();

		while (startAt < totalResults)
		{
			Promise<SearchResult> mongoRes = jiraUtil.getIssuesFromMongoDBProject(startAt, 1000);
			addIssuesFromProject(mongoRes, JiraUtil.JIRA_MONGODB_PROJECTKEY);
			startAt += 1000;
		}
	}

	public void addIssuesFromCamundaProject()
	{
		int startAt = 0;
		int totalResults = jiraUtil.getTotalIssueCountFromCamundaProject();

		while (startAt < totalResults)
		{
			Promise<SearchResult> camundaRes = jiraUtil.getIssuesFromCamundaProject(startAt, 1000);
			addIssuesFromProject(camundaRes, JiraUtil.JIRA_CAMUNDA_PROJECTKEY);
			startAt += 1000;
		}
	}

	public void addIssuesFromProject(Promise<SearchResult> projRes, String projectKey)
	{
		if (projRes != null)
		{
			addIssuesToDatabase(projRes, projectKey);
		}

		jiraUtil.closeClientConnection();
	}

	public IssueDbContext getIssueDbContext()
	{
		return idc;
	}

	public JiraUtil getJiraUtil()
	{
		return jiraUtil;
	}
}
