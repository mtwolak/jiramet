package jira.connector;

public class IssueDownloaderMain
{

	public static void main(String[] args) throws Exception
	{
		IssueDownloader id = new IssueDownloader();
		id.retriveAllIssues();
		//id.getIssueDbContext().initDbm();
		//id.addIssuesFromSpringProject();
		//id.getIssueDbContext().closeDbm();

		System.exit(0);
	}

}
