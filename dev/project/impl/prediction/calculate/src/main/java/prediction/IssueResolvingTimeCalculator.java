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
		double sumAlpha = 0;

		for(JiraIssueSimilarity jis : issuesWithSimilarity)
		{
			AssignedIssue currentAssignedIssue = jis.getJiraIssue().getAssignedIssues().iterator().next();
			double resolveTime = ResolveTimeCalculator.getResolveTime(currentAssignedIssue);

			result += resolveTime * jis.getSimilarityLevel();
			sumAlpha += jis.getSimilarityLevel();
		}
		return Math.round((result / sumAlpha)*100.0)/100.0;
	}

}
