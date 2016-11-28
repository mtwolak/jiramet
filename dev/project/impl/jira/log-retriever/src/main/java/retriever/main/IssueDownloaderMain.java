package retriever.main;

import jira.JiraWebLogDownloader;
import jira.anonymization.NameRandomizer;
import jira.connector.IssueDownloader;
import jira.data.ProjectData;
import utils.properties.hibernate.HibernateProductionConfiguration;

public class IssueDownloaderMain implements JiraWebLogDownloader
{

	@Override
	public void retrieveAllIssues()
	{
		IssueDownloader id = new IssueDownloader(new HibernateProductionConfiguration());
		id.retrieveAllIssues();
		NameRandomizer.randomizeAllNames(new HibernateProductionConfiguration());
	}

}
