package jira.connector;

public class IssueDownloaderMain {
	
	 public static void main(String[] args) throws Exception 
	    {
		 
	    	IssueDownloader id = new IssueDownloader();
	    	id.getIssueDbContext().initDbm();
	    	//id.addIssuesFromCamundaProject();
	    	//id.addIssuesFromMongoDBProject();
	    	id.addIssuesFromSpringProject();
	    	id.getIssueDbContext().closeDbm();
		    	
	        System.exit(0);
	    }

}
