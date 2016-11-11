package jira;

import java.net.URL;
import java.util.List;

import database.entity.JiraIssue;

public interface JiraWebLogDownloader {
	public List<JiraIssue> retrieveAllIssues(URL jiraWebUrl);

}
