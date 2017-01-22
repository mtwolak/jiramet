package prediction;

import java.util.List;

import database.entity.AssignedIssue;
import jira.JiraIssueSimilarity;
import utils.time.ResolveTimeCalculator;

/**
 * Contains methods responsible for calculating the time needed to resolve the selected issue
 *
 */
public class IssueResolvingTimeCalculator
{

	/**
	 * Returns the time needed to resolve the selected issue, calculated as described in our model
	 * 
	 * @param issuesWithSimilarity collection of JIRA issues with its similarity level comparing to the main issue
	 * @return estimated time needed to resolve the selected issue
	 * @see JiraIssueSimilarity
	 */
	public double getIssuesResolvingTime(List<JiraIssueSimilarity> issuesWithSimilarity)
	{
		if (issuesWithSimilarity == null || issuesWithSimilarity.isEmpty())
		{
			return -1;
		}
		double result = 0;
		double normalizationFactor = getNormalizationFactor(issuesWithSimilarity);

		for (JiraIssueSimilarity jis : issuesWithSimilarity)
		{
			AssignedIssue currentAssignedIssue = jis.getJiraIssue().getAssignedIssues().iterator().next();
			double resolveTime = ResolveTimeCalculator.getResolveTime(currentAssignedIssue);
			result += resolveTime * jis.getSimilarityLevel() * normalizationFactor;
		}
		return result;
	}

	private double getNormalizationFactor(List<JiraIssueSimilarity> issuesWithSimilarity)
	{
		double sumAlpha = 0;
		for (JiraIssueSimilarity jis : issuesWithSimilarity)
		{
			sumAlpha += jis.getSimilarityLevel();
		}
		return 1.0 / sumAlpha;
	}

}
