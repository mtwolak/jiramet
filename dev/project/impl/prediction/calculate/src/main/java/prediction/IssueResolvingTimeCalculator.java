package prediction;

import java.util.List;

import database.entity.AssignedIssue;
import jira.JiraIssueSimilarity;
import utils.time.ResolveTimeCalculator;

public class IssueResolvingTimeCalculator
{

	public double getIssuesResolvingTime(List<JiraIssueSimilarity> issuesWithSimilarity)
	{
		double result = 0;
		double normalizationFactor = getNormalizationFactor(issuesWithSimilarity);

		for(JiraIssueSimilarity jis : issuesWithSimilarity)
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
		for(JiraIssueSimilarity jis : issuesWithSimilarity)
		{
			sumAlpha += jis.getSimilarityLevel();
		}
		return 1.0 / sumAlpha;
	}

}
