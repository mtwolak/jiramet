package jira;

import database.entity.JiraIssue;

public class JiraIssueSimilarity implements Comparable<JiraIssueSimilarity> {

	private JiraIssue jiraIssue;
	private Long similarityLevel;
	
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

	@Override
	public int compareTo(JiraIssueSimilarity o)
	{
		return this.similarityLevel.compareTo(o.similarityLevel);
	}


	
}
