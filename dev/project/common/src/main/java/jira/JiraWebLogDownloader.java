package jira;

import java.net.URL;
import java.util.List;

import jira.issue.JiraIssue;

public interface JiraWebLogDownloader {
	public List<JiraIssue> retrieveAllIssues(URL jiraWebUrl);

}
