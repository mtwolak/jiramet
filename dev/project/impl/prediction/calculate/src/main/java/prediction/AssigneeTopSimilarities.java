package prediction;

import java.util.ArrayList;
import java.util.List;

import database.entity.Assignee;
import jira.JiraIssueSimilarity;

public class AssigneeTopSimilarities
{
	private Assignee assignee;
	private List<JiraIssueSimilarity> jiraIssueSimilarities;

	public AssigneeTopSimilarities(int max_results)
	{
		this.jiraIssueSimilarities = new ArrayList<JiraIssueSimilarity>(max_results);
	}

	public Assignee getAssignee()
	{
		return assignee;
	}

	public void setAssignee(Assignee assignee)
	{
		this.assignee = assignee;
	}

	public List<JiraIssueSimilarity> getJiraIssueSimilarities()
	{
		return jiraIssueSimilarities;
	}

	public void addJiraIssueSimilarity(JiraIssueSimilarity newJiraIssueSimilarity)
	{
		jiraIssueSimilarities.add(newJiraIssueSimilarity);
	}

}
