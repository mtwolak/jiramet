package jira.connector;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.util.concurrent.Promise;

import database.entity.JiraProject;
import jira.data.IssueDbContext;
import jira.project.ProjectData;
import utils.properties.hibernate.HibernateConfiguration;

public class IssueDownloader
{

	private JiraUtil jiraUtil;
	private IssueDbContext idc;

	public IssueDownloader(HibernateConfiguration hibernateConfiguration)
	{
		jiraUtil = new JiraUtil();
		idc = new IssueDbContext(hibernateConfiguration);
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
		int counter = startAt;

		while (counter < totalResults)
		{
			Promise<SearchResult> springRes = jiraUtil.getIssuesFromProject(project, counter, project.getIssueLimitPerCall());
			addIssuesFromProject(springRes, project);
			counter += project.getIssueLimitPerCall();
		}
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
	
}
