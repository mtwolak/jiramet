package jira.connector;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.auth.AnonymousAuthenticationHandler;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.atlassian.util.concurrent.Promise;
import java.net.URI;

public class JiraUtil {
	
	private static final String JIRA_SPRING_URL = "https://jira.spring.io";
	private static final String JIRA_SPRING_FRAMEWORK_PROJECTKEY = "SPR";
	private static final String JIRA_MONGODB_URL = "https://jira.mongodb.org";
	private static final String JIRA_MONGODB_PROJECTKEY = "SERVER";
	private static final String JIRA_CAMUNDA_URL = "https://app.camunda.com/jira/";
	private static final String JIRA_CAMUNDA_PROJECTKEY = "CAM";
	
	private JiraRestClient client;
	
    public static void main(String[] args) throws Exception 
    {
    	JiraUtil ju = new JiraUtil();
    	Promise<SearchResult> springRes = ju.getIssuesFromSpringProject();

    	if(springRes != null)
    	{
    		for (Issue issue : springRes.claim().getIssues()) {
		        System.out.println(issue.getKey());                      //tu po prostu metodami mozna pobierac wszystkie parametry z danego issue
    		}
    	}

    	ju.closeClientConnection();
	    	
        System.exit(0);
    }
    
    private Promise<SearchResult> getIssuesFromProject(String projectURL, String projectKey)
    {
    	try
    	{
	        JiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
	        URI uri = new URI(projectURL);
	        client = factory.create(uri, new AnonymousAuthenticationHandler());
	        
	        Promise<SearchResult> searchJqlPromise = client.getSearchClient()
	        		.searchJql("project =" + projectKey + " AND status in (Resolved, Closed)", 1500, 0, null);
	       	       	        
	        return searchJqlPromise;
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return null;
    	}
    }
    
    public Promise<SearchResult> getIssuesFromSpringProject()
    {
    	return getIssuesFromProject(JIRA_SPRING_URL, JIRA_SPRING_FRAMEWORK_PROJECTKEY);
    }
    
    public Promise<SearchResult> getIssuesFromMongoDBProject()
    {
    	return getIssuesFromProject(JIRA_MONGODB_URL, JIRA_MONGODB_PROJECTKEY);
    }
    
    public Promise<SearchResult> getIssuesFromCamundaProject()
    {
    	return getIssuesFromProject(JIRA_CAMUNDA_URL, JIRA_CAMUNDA_PROJECTKEY);
    }
    
    public void closeClientConnection()
    {
    	try
    	{
    		if(client != null)
    			client.close();
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
}
