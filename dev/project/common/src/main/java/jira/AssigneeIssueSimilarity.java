package jira;

import java.util.List;

import database.entity.Assignee;
/**
 * Holds information about assignee and his issues with similarity level to other issue
 *
 */
public class AssigneeIssueSimilarity
{
	private Assignee assignee;
	private List<JiraIssueSimilarity> jiraIssueSimilarities;
	
	/**
	 * Constructs object with assignee and jira issues with similarity lever
	 * @param assignee assignee
	 * @param jiraIssueSimilarities jira issues with similarity level for given assignee
	 */
	public AssigneeIssueSimilarity(Assignee assignee, List<JiraIssueSimilarity> jiraIssueSimilarities)
	{
		this.assignee = assignee;
		this.jiraIssueSimilarities = jiraIssueSimilarities;
	}
	
	/**
	 * Gets assignee
	 * @return jira assignee
	 */
	public Assignee getAssignee() {
		return assignee;
	}

	/**
	 * Sets assignee
	 * @param assignee jira assignee
	 */
	public void setAssignee(Assignee assignee) {
		this.assignee = assignee;
	}
	
	/**
	 * Gets assigned jira issues with similarity
	 * @return jira issues with similarity
	 */
	public List<JiraIssueSimilarity> getAssignedJiraIssues() {
		return jiraIssueSimilarities;
	}
	
	/**
	 * Sets jira issues with similarity
	 * @param jiraIssueSimilarities jira issue with similarity
	 */
	public void setAssigneedIssues(List<JiraIssueSimilarity> jiraIssueSimilarities) {
		this.jiraIssueSimilarities = jiraIssueSimilarities;
	}
}
