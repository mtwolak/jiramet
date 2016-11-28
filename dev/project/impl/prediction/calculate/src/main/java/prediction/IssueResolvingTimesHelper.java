package prediction;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import database.entity.AssignedIssue;
import database.entity.Assignee;
import jira.JiraIssueSimilarity;

public class IssueResolvingTimesHelper
{
	public Set<Assignee> extractAssignees(List<JiraIssueSimilarity> issueSimilarities)
	{
		Set<Assignee> allAssignees = new HashSet<Assignee>();
		for (JiraIssueSimilarity jis : issueSimilarities)
		{
			Set<AssignedIssue> temp = jis.getJiraIssue().getAssignedIssues();

			if (temp.size() == 1 && temp.iterator().next().getResolvedAt() != null)
			{
				allAssignees.add(temp.iterator().next().getAssignee());
			}
		}
		return allAssignees;
	}

	public List<JiraIssueSimilarity> getIssuesSimilaritiesForAssignee(Assignee assignee,
			List<JiraIssueSimilarity> issueSimilarities)
	{
		if (assignee == null)
		{
			return null;
		}
		List<JiraIssueSimilarity> result = new ArrayList<JiraIssueSimilarity>();
		for (JiraIssueSimilarity jis : issueSimilarities)
		{
			if (jis.getJiraIssue().getAssignedIssues().iterator().next().getAssignee().equals(assignee))
			{
				result.add(jis);
			}
		}
		return result;
	}

	public double getIssuesResolvingTime(AssigneeTopSimilarities assigneeTopSimilarities)
	{
		double result = 0;
		double sumAlpha = 0;

		for (JiraIssueSimilarity jis : assigneeTopSimilarities.getJiraIssueSimilarities())
		{
			AssignedIssue currentAssignedIssue = jis.getJiraIssue().getAssignedIssues().iterator().next();
			Timestamp start = currentAssignedIssue.getJiraIssue().getCreatedAt();
			Timestamp finish = currentAssignedIssue.getResolvedAt();
			double resolveTime = getResolveTimeInDays(start, finish);

			result += resolveTime * jis.getSimilarityLevel();
			sumAlpha += jis.getSimilarityLevel();
		}
		return Math.round((result / sumAlpha)*100.0)/100.0; //potwierdzcie czy zaokraglac czy lepiej nie
	}

	private double getResolveTimeInDays(Timestamp start, Timestamp finish)
	{
		return (finish.getTime() - start.getTime()) / (1000 * 60 * 60 * 24);
	}

}
