package jira.connector;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.auth.AnonymousAuthenticationHandler;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.atlassian.util.concurrent.Promise;

import java.net.URI;

public class JiraUtil
{

	public static final String JIRA_SPRING_URL = "https://jira.spring.io";
	public static final String JIRA_SPRING_FRAMEWORK_PROJECTKEY = "SPR";
	public static final String JIRA_MONGODB_URL = "https://jira.mongodb.org";
	public static final String JIRA_MONGODB_PROJECTKEY = "SERVER";
	public static final String JIRA_CAMUNDA_URL = "https://app.camunda.com/jira";
	public static final String JIRA_CAMUNDA_PROJECTKEY = "CAM";

	private JiraRestClient client;

	private Promise<SearchResult> getIssuesFromProject(String projectURL, String projectKey, int startAt, int maxResults)
	{
		try
		{
			JiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
			URI uri = new URI(projectURL);
			client = factory.create(uri, new AnonymousAuthenticationHandler());

			Promise<SearchResult> searchJqlPromise = client.getSearchClient()
					.searchJql("project =" + projectKey + " AND status in (Resolved, Closed)", maxResults, startAt, null);

			return searchJqlPromise;
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	private int getTotalIssueCountFromProject(String projectURL, String projectKey)
	{
		return getIssuesFromProject(projectURL, projectKey, 0, 1).claim().getTotal();
	}

	public Promise<SearchResult> getIssuesFromSpringProject(int startAt, int maxResults)
	{
		return getIssuesFromProject(JIRA_SPRING_URL, JIRA_SPRING_FRAMEWORK_PROJECTKEY, startAt, maxResults);
	}

	public Promise<SearchResult> getIssuesFromMongoDBProject(int startAt, int maxResults)
	{
		return getIssuesFromProject(JIRA_MONGODB_URL, JIRA_MONGODB_PROJECTKEY, startAt, maxResults);
	}

	public Promise<SearchResult> getIssuesFromCamundaProject(int startAt, int maxResults)
	{
		return getIssuesFromProject(JIRA_CAMUNDA_URL, JIRA_CAMUNDA_PROJECTKEY, startAt, maxResults);
	}
	
	public int getTotalIssueCountFromSpringProject()
	{
		return getTotalIssueCountFromProject(JIRA_SPRING_URL, JIRA_SPRING_FRAMEWORK_PROJECTKEY);
	}

	public int getTotalIssueCountFromMongoDBProject()
	{
		return getTotalIssueCountFromProject(JIRA_MONGODB_URL, JIRA_MONGODB_PROJECTKEY);
	}

	public int getTotalIssueCountFromCamundaProject()
	{
		return getTotalIssueCountFromProject(JIRA_CAMUNDA_URL, JIRA_CAMUNDA_PROJECTKEY);
	}

	public void closeClientConnection()
	{
		try
		{
			if (client != null)
				client.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public JiraRestClient getClient()
	{
		return client;
	}
}
