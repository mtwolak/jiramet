package jira.connector;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.auth.AnonymousAuthenticationHandler;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.atlassian.util.concurrent.Promise;

import jira.project.ProjectData;

public class JiraUtilTest {
		
	private JiraUtil ju = new JiraUtil();
	private JiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
	
	private Promise<SearchResult> searchResult;
	private JiraRestClient client;
	private URI uri;
	
	@Test
	public void testOpenSpringClientConnection() 
	{
		try {
			uri = new URI(ProjectData.SPRING.getProjectURL());
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
			uri = new URI(ProjectData.MONGODB.getProjectURL());
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
			uri = new URI(ProjectData.CAMUNDA.getProjectURL());
			client = factory.create(uri, new AnonymousAuthenticationHandler());
			assertNotNull(client);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetIssuesFromSpringProject() 
	{		
		searchResult = ju.getIssuesFromProject(ProjectData.SPRING, 0,1);
		assertNotNull(searchResult);
	}
	
	@Test
	public void testGetIssuesFromMongoDBProject() 
	{
		searchResult = ju.getIssuesFromProject(ProjectData.MONGODB, 0,1);
		assertNotNull(searchResult);
	}
	
	@Test
	public void testGetIssuesFromCamundaProject() 
	{
		searchResult = ju.getIssuesFromProject(ProjectData.CAMUNDA, 0,1);
		assertNotNull(searchResult);
	}
	
	@Test
	public void testCloseClientConnection() 
	{
		ju.closeClientConnection();
		assertNull(ju.getClient());
	}

	@Test
	public void testGetTotalIssueCountFromSpringProject()
	{
		assertThat(ju.getTotalIssueCountFromProject(ProjectData.SPRING), instanceOf(Integer.class));
	}

	@Test
	public void testGetTotalIssueCountFromMongoDBProject()
	{
		assertThat(ju.getTotalIssueCountFromProject(ProjectData.MONGODB), instanceOf(Integer.class));
	}

	@Test
	public void testGetTotalIssueCountFromCamundaProject()
	{
		assertThat(ju.getTotalIssueCountFromProject(ProjectData.CAMUNDA), instanceOf(Integer.class));
	}

}
