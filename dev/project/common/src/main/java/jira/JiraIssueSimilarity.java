package jira;

import database.entity.JiraIssue;
/**
 * Jira issue with similarity level to other issue
 *
 */
public class JiraIssueSimilarity implements Comparable<JiraIssueSimilarity> {

	private JiraIssue jiraIssue;
	private Double similarityLevel;
	
	/**
	 * Holds information about jira issue and its similarity level to other issue
	 * @param jiraIssue jira issue
	 * @param similarityLevel similarity level
	 */
	public JiraIssueSimilarity(JiraIssue jiraIssue, double similarityLevel)
	{
		this.jiraIssue = jiraIssue;
		this.similarityLevel = similarityLevel;
	}

	/**
	 * Gets jira issue
	 * @return jira issue
	 */
	public JiraIssue getJiraIssue() {
		return jiraIssue;
	}

	/**
	 * Sets jira issue
	 * @param jiraIssue jira issue
	 */
	public void setJiraIssue(JiraIssue jiraIssue) {
		this.jiraIssue = jiraIssue;
	}

	/**
	 * Gets similarity level
	 * @return similarity level
	 */
	public double getSimilarityLevel() {
		return similarityLevel;
	}

	/**
	 * Sets similarity level
	 * @param similarityLevel similarity level
	 */
	public void setSimilarityLevel(double similarityLevel) {
		this.similarityLevel = similarityLevel;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(JiraIssueSimilarity o)
	{
		return Double.compare(similarityLevel, o.similarityLevel);
	}


	
}
