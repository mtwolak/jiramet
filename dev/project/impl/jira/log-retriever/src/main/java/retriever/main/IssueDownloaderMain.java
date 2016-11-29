package retriever.main;

import jira.JiraWebLogDownloader;
import jira.anonymization.NameRandomizer;
import jira.connector.IssueDownloader;
import jira.project.ProjectData;
import utils.properties.hibernate.HibernateProductionConfiguration;

public class IssueDownloaderMain implements JiraWebLogDownloader
{
	IssueDownloader id;

	public IssueDownloaderMain()
	{
		id = new IssueDownloader(new HibernateProductionConfiguration());
	}

	@Override
	public void retrieveAllIssues()
	{
		id.retrieveAllIssues();
		NameRandomizer.randomizeAllNames(new HibernateProductionConfiguration());
	}

	@Override
	public void retrieveIssuesFromProject(ProjectData project)
	{
		id.downloadAllIssuesFromProject(project);
		NameRandomizer.randomizeAllNames(new HibernateProductionConfiguration());
	}

}
