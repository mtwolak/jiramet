package similarity;

import java.util.Collections;
import java.util.List;

import jira.JiraIssueSimilarity;

public class ListPartitioner
{
	private int maxElementsInList;

	public ListPartitioner(int maxElementsInList)
	{
		this.maxElementsInList = maxElementsInList;
	}

	public List<JiraIssueSimilarity> getList(List<JiraIssueSimilarity> jiraIssueSimilarities)
	{
		return jiraIssueSimilarities.isEmpty() ? jiraIssueSimilarities : partition(jiraIssueSimilarities);
	}

	private List<JiraIssueSimilarity> partition(List<JiraIssueSimilarity> jiraIssueSimilarities)
	{
		Collections.sort(jiraIssueSimilarities);
		int elementsInList = maxElementsInList > jiraIssueSimilarities.size() ? jiraIssueSimilarities.size() : maxElementsInList;
		return jiraIssueSimilarities.subList(0, elementsInList);
	}

}
