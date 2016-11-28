package jira;

import database.entity.JiraIssue;

public class JiraIssueSimilarity implements Comparable<JiraIssueSimilarity> {

	private JiraIssue jiraIssue;
	private Double similarityLevel;
	
	public JiraIssueSimilarity(JiraIssue jiraIssue, double similarityLevel)
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

	public double getSimilarityLevel() {
		return similarityLevel;
	}

	public void setSimilarityLevel(double similarityLevel) {
		this.similarityLevel = similarityLevel;
	}

	@Override
	public int compareTo(JiraIssueSimilarity o)
	{
		return Double.compare(this.similarityLevel, o.similarityLevel);
	}


	
}
