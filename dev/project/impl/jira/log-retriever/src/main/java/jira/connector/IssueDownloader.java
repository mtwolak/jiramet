package jira.connector;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.util.concurrent.Promise;

import database.entity.JiraProject;
import database.manager.DataBaseType;
import jira.data.IssueDbContext;
import jira.data.ProjectData;

public class IssueDownloader
{

	private JiraUtil jiraUtil;
	private IssueDbContext idc;

	public IssueDownloader(DataBaseType dataBaseType)
	{
		jiraUtil = new JiraUtil();
		idc = new IssueDbContext(dataBaseType);
	}

	public void retrieveAllIssues()
	{
		for (ProjectData project : ProjectData.values())
		{
			downloadAllIssuesFromProject(project);
		}
	}

	private void addIssuesToDatabase(Promise<SearchResult> searchRes, ProjectData project)
	{
		JiraProject addedJiraProject = IssueDownloaderUtil.addProjectToDatabase(idc, project);

		if (addedJiraProject != null)
		{
			for (Issue issue : searchRes.claim().getIssues())
			{
				IssueDownloaderUtil.addSingleIssueToDatabase(idc, project, issue);
			}
		}
	}

	public void downloadIssuesFromProject(ProjectData project, int startAt, int totalResults)
	{
		initIssueDbContext();

		while (startAt < totalResults)
		{
			Promise<SearchResult> springRes = jiraUtil.getIssuesFromProject(project, startAt, project.getIssueLimitPerCall());
			addIssuesFromProject(springRes, project);
			startAt += project.getIssueLimitPerCall();
		}
		closeIssueDbContext();
	}
	
	public void downloadAllIssuesFromProject(ProjectData project)
	{
		int totalResults = jiraUtil.getTotalIssueCountFromProject(project);
		downloadIssuesFromProject(project, 0, totalResults);
	}

	public void addIssuesFromProject(Promise<SearchResult> projRes, ProjectData project)
	{
		if (projRes != null)
		{
			addIssuesToDatabase(projRes, project);
		}

		jiraUtil.closeClientConnection();
	}

	public void  initIssueDbContext()
	{
		idc.initDbm();
	}
	
	public void  closeIssueDbContext()
	{
		idc.closeDbm();
	}
	
}
