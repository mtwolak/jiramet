package prediction;

import java.sql.Timestamp;
import java.util.List;

import database.entity.AssignedIssue;
import jira.JiraIssueSimilarity;

public class IssueResolvingTimeCalculator
{

	public double getIssuesResolvingTime(List<JiraIssueSimilarity> assigneeTopSimilarities)
	{
		double result = 0;
		double sumAlpha = 0;

		for(JiraIssueSimilarity jis : assigneeTopSimilarities)
		{
			AssignedIssue currentAssignedIssue = jis.getJiraIssue().getAssignedIssues().iterator().next();
			Timestamp start = currentAssignedIssue.getJiraIssue().getCreatedAt();
			Timestamp finish = currentAssignedIssue.getResolvedAt();
			double resolveTime = getResolveTimeInDays(start, finish);

			result += resolveTime * jis.getSimilarityLevel();
			sumAlpha += jis.getSimilarityLevel();
		}
		return Math.round((result / sumAlpha)*100.0)/100.0;
	}

	private double getResolveTimeInDays(Timestamp start, Timestamp finish)
	{
		return (finish.getTime() - start.getTime()) / (1000 * 60 * 60 * 24);
	}

}