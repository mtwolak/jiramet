package jira.connector;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.auth.AnonymousAuthenticationHandler;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.atlassian.util.concurrent.Promise;

import jira.project.ProjectData;

import java.net.URI;

/**
 * Provides connection with JIRA, by using JIRA REST API client.  
 *
 */
public class JiraUtil
{
	
	private JiraRestClient client;

	/**
	 * Returns specified number of issues from the selected JIRA project.
	 * Only issues with status Resolved or Closed are considered.
	 * 
	 * @param project essential data about the JIRA project
	 * @param startAt id of the first issue, which indicate the download starting point
	 * @param maxResults maximum number of issues that should be downloaded, the number can not be greater then 1000
	 * @return collection of downloaded issues
	 * @see ProjectData
	 */
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
	/**
	 * Returns total number of issues located in the selected JIRA project
	 * 
	 * @param project essential data about the JIRA project
	 * @return total number of issues matching given criteria
	 * @see ProjectData
	 */
	public int getTotalIssueCountFromProject(ProjectData project)
	{
		return getIssuesFromProject(project, 0, 1).claim().getTotal();
	}
	/**
	 * Closes the connection with JIRA REST API client
	 */
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
	/**
	 * 
	 * @return instance of JIRA REST API client
	 */
	public JiraRestClient getClient()
	{
		return client;
	}
}
