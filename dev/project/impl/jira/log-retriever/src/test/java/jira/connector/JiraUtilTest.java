package jira.connector;

import static org.junit.Assert.*;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.auth.AnonymousAuthenticationHandler;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.atlassian.util.concurrent.Promise;

public class JiraUtilTest {
	
	private static final String JIRA_SPRING_URL = "https://jira.spring.io";
	private static final String JIRA_MONGODB_URL = "https://jira.mongodb.org";
	private static final String JIRA_CAMUNDA_URL = "https://app.camunda.com/jira/";
	
	private JiraUtil ju = new JiraUtil();
	private JiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
	
	private Promise<SearchResult> searchResult;
	private JiraRestClient client;
	private URI uri;
	
	@Test
	public void testOpenSpringClientConnection() 
	{
		try {
			uri = new URI(JIRA_SPRING_URL);
			client = factory.create(uri, new AnonymousAuthenticationHandler());
			assertNotNull(client);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testOpenMongoDBClientConnection() 
	{
		try {
			uri = new URI(JIRA_MONGODB_URL);
			client = factory.create(uri, new AnonymousAuthenticationHandler());
			assertNotNull(client);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testOpenCamundaClientConnection() 
	{
		try {
			uri = new URI(JIRA_CAMUNDA_URL);
			client = factory.create(uri, new AnonymousAuthenticationHandler());
			assertNotNull(client);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetIssuesFromSpringProject() 
	{		
		searchResult = ju.getIssuesFromSpringProject();
		assertNotNull(searchResult);
	}
	
	@Test
	public void testGetIssuesFromMongoDBProject() 
	{
		searchResult = ju.getIssuesFromMongoDBProject();
		assertNotNull(searchResult);
	}
	
	@Test
	public void testGetIssuesFromCamundaProject() 
	{
		searchResult = ju.getIssuesFromCamundaProject();
		assertNotNull(searchResult);
	}
	
	@Test
	public void testCloseClientConnection() 
	{
		ju.closeClientConnection();
		assertNull(client);
	}

}
