package retriever.main;

import database.manager.DataBaseType;
import jira.JiraWebLogDownloader;
import jira.anonymization.NameRandomizer;
import jira.connector.IssueDownloader;

public class IssueDownloaderMain implements JiraWebLogDownloader
{
	@Override
	public void retrieveAllIssues()
	{
		IssueDownloader id = new IssueDownloader(DataBaseType.PRODUCTION);
		id.retrieveAllIssues();
		NameRandomizer.randomizeAllNames(DataBaseType.PRODUCTION);
	}

}
