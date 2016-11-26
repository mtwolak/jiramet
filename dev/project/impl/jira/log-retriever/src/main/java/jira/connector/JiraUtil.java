package jira.connector;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.auth.AnonymousAuthenticationHandler;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.atlassian.util.concurrent.Promise;

import jira.data.ProjectData;

import java.net.URI;

public class JiraUtil
{

	private JiraRestClient client;

	public Promise<SearchResult> getIssuesFromProject(ProjectData project, int startAt, int maxResults)
	{
		try
		{
			JiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
			URI uri = new URI(project.getProjectURL());
			client = factory.create(uri, new AnonymousAuthenticationHandler());

			Promise<SearchResult> searchJqlPromise = client.getSearchClient()
					.searchJql("project =" + project.getProjectKey() + " AND status in (Resolved, Closed)", maxResults, startAt, null);

			return searchJqlPromise;
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public int getTotalIssueCountFromProject(ProjectData project)
	{
		return getIssuesFromProject(project, 0, 1).claim().getTotal();
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
