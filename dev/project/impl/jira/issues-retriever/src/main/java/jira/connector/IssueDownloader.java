package jira.connector;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.util.concurrent.Promise;

import database.entity.JiraProject;
import jira.data.IssueDbContext;
import jira.project.ProjectData;
import utils.properties.hibernate.HibernateConfiguration;

/**
 * Class contains a set of methods, which main goal is to extract data about issues stored in JIRA projects
 *
 */
public class IssueDownloader
{

	private JiraUtil jiraUtil;
	private IssueDbContext idc;

	/**
	 * Creates a new instance of IssueDownloader class and initialize all necessary variables
	 * 
	 * @param hibernateConfiguration database properties
	 * @see HibernateConfiguration
	 */
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

	/**
	 * Downloads defined number of issues from the selected JIRA project and puts them to the local database
	 * 
	 * @param project essential data about the JIRA project, from which issues should be downloaded
	 * @param startAt id of the first issue, which indicate the download starting point
	 * @param totalResults total number of issues that should be downloaded
	 * @see ProjectData
	 */
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
	/**
	 * Downloads all issues from the selected JIRA project and puts them to the local database
	 * 
	 * @param project essential data about the JIRA project, from which issues should be downloaded
	 * @see ProjectData
	 */
	public void downloadAllIssuesFromProject(ProjectData project)
	{
		int totalResults = jiraUtil.getTotalIssueCountFromProject(project);
		downloadIssuesFromProject(project, 0, totalResults);
	}

	/**
	 * Adds selected issues from specified JIRA project to the local database
	 * 
	 * @param projRes collection of JIRA issues
	 * @param project essential data about the JIRA project, from which issues have been downloaded
	 * @see ProjectData
	 */
	public void addIssuesFromProject(Promise<SearchResult> projRes, ProjectData project)
	{
		if (projRes != null)
		{
			addIssuesToDatabase(projRes, project);
		}

		jiraUtil.closeClientConnection();
	}

	/**
	 * Initializes the database context
	 * @see IssueDbContext
	 */
	public void initIssueDbContext()
	{
		idc.initDbm();
	}
	
}
