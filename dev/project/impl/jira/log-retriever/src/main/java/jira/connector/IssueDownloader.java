package jira.connector;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.util.concurrent.Promise;

import jira.data.IssueDbContext;

public class IssueDownloader {

	private JiraUtil jiraUtil;
	private IssueDbContext idc;

	public IssueDownloader() {
		jiraUtil = new JiraUtil();
		idc = new IssueDbContext();
	}

	private void addIssuesToDatabase(Promise<SearchResult> searchRes, String projectKey) {
		String projectName = IssueDownloaderUtil.addProjectToDatabase(idc, projectKey);

		if (projectName != "") {
			for (Issue issue : searchRes.claim().getIssues()) {
				IssueDownloaderUtil.addSingleIssueToDatabase(idc, projectName, issue);
			}
		}
	}

	public void addIssuesFromSpringProject() {
		Promise<SearchResult> springRes = jiraUtil.getIssuesFromSpringProject();
		addIssuesFromProject(springRes, JiraUtil.JIRA_SPRING_FRAMEWORK_PROJECTKEY);
	}

	public void addIssuesFromMongoDBProject() {
		Promise<SearchResult> mongoRes = jiraUtil.getIssuesFromMongoDBProject();
		addIssuesFromProject(mongoRes, JiraUtil.JIRA_MONGODB_PROJECTKEY);
	}

	public void addIssuesFromCamundaProject() {
		Promise<SearchResult> camundaRes = jiraUtil.getIssuesFromCamundaProject();
		addIssuesFromProject(camundaRes, JiraUtil.JIRA_CAMUNDA_PROJECTKEY);
	}

	private void addIssuesFromProject(Promise<SearchResult> projRes, String projectKey) {
		if (projRes != null) {
			addIssuesToDatabase(projRes, projectKey);
		}

		jiraUtil.closeClientConnection();
	}

	public IssueDbContext getIssueDbContext() {
		return idc;
	}
}
