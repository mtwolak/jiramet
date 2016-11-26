package retriever.main;

import database.manager.DataBaseType;
import jira.JiraWebLogDownloader;
import jira.connector.IssueDownloader;
import jira.data.ProjectData;

public class IssueDownloaderMain implements JiraWebLogDownloader
{

	public static void main(String[] args) throws Exception
	{
		IssueDownloader id = new IssueDownloader(DataBaseType.PRODUCTION);
		id.downloadAllIssuesFromProject(ProjectData.MONGODB);
	}

	@Override
	public void retrieveAllIssues()
	{
		IssueDownloader id = new IssueDownloader(DataBaseType.PRODUCTION);
		id.retrieveAllIssues();
	}

}
