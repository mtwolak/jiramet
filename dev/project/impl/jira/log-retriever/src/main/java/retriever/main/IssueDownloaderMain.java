package retriever.main;

import database.manager.DataBaseType;
import jira.connector.IssueDownloader;

public class IssueDownloaderMain
{

	public static void main(String[] args) throws Exception
	{
		IssueDownloader id = new IssueDownloader(DataBaseType.PRODUCTION);
		//id.retriveAllIssues();
		id.getIssueDbContext().initDbm();
		id.addIssuesFromSpringProject();
		id.getIssueDbContext().closeDbm();
	}

}
