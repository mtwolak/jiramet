package retriever.main;

import jira.JiraWebLogDownloader;
import jira.anonymization.NameRandomizer;
import jira.connector.IssueDownloader;
import jira.data.ProjectData;
import utils.properties.hibernate.HibernateProductionConfiguration;

public class IssueDownloaderMain implements JiraWebLogDownloader
{

	public static void main(String[] args) throws Exception
	{
		IssueDownloader id = new IssueDownloader(new HibernateProductionConfiguration());
		id.downloadAllIssuesFromProject(ProjectData.MONGODB);
	}

	@Override
	public void retrieveAllIssues()
	{
		IssueDownloader id = new IssueDownloader(new HibernateProductionConfiguration());
		id.retrieveAllIssues();
		NameRandomizer.randomizeAllNames(new HibernateProductionConfiguration());
	}

}
