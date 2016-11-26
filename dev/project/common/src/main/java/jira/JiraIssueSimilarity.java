package jira;

import database.entity.JiraIssue;

public class JiraIssueSimilarity {

	private JiraIssue jiraIssue;
	private long similarityLevel;
	
	public JiraIssueSimilarity(JiraIssue jiraIssue, long similarityLevel)
	{
		this.jiraIssue = jiraIssue;
		this.similarityLevel = similarityLevel;
	}

	public JiraIssue getJiraIssue() {
		return jiraIssue;
	}

	public void setJiraIssue(JiraIssue jiraIssue) {
		this.jiraIssue = jiraIssue;
	}

	public long getSimilarityLevel() {
		return similarityLevel;
	}

	public void setSimilarityLevel(long similarityLevel) {
		this.similarityLevel = similarityLevel;
	}

	
}
