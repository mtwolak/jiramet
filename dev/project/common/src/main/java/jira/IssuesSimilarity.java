package jira;

import java.util.List;

import database.entity.JiraIssue;

public interface IssuesSimilarity
{
	public List<JiraIssueSimilarity> getIssuesSimilarity(JiraIssue jiraIssue);
}
