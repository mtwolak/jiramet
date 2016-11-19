package jira.connector;

public class IssueDownloaderMain
{

	public static void main(String[] args) throws Exception
	{
		IssueDownloader id = new IssueDownloader();
		id.retriveAllIssues();

		System.exit(0);
	}

}
