package jira.connector;

public class IssueDownloaderMain {
	
	 public static void main(String[] args) throws Exception 
	    {
		 
	    	IssueDownloader id = new IssueDownloader();
	    	id.getIssueDbContext().initDbm();
	    	//id.addIssuesFromCamundaProject();
	    	//id.addIssuesFromMongoDBProject();
	    	id.addIssuesFromSpringProject();
		    	
	        System.exit(0);
	    }

}
