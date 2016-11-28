package prediction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import database.entity.Assignee;
import jira.AssigneeTimeResolve;
import jira.JiraIssueSimilarity;

public class IssueResolvingTimes
{

	private static final double MIN_ALPHA = 0.01;
	private static final int MAX_RESULTS = 5; // k from model
	IssueResolvingTimesHelper helper;

	public IssueResolvingTimes()
	{
		helper = new IssueResolvingTimesHelper();
	}

	public List<AssigneeTimeResolve> getIssueResolvingTimePrediction(List<JiraIssueSimilarity> issueSimilarities)
	{
		Set<Assignee> allAssignees = helper.extractAssignees(issueSimilarities);

		if (allAssignees.isEmpty())
		{
			return null;
		}

		ArrayList<AssigneeTimeResolve> result = new ArrayList<AssigneeTimeResolve>();

		for (Assignee assignee : allAssignees)
		{
			List<JiraIssueSimilarity> assigneeIssueSimilarities = helper.getIssuesSimilaritiesForAssignee(assignee,
					issueSimilarities);

			if (assigneeIssueSimilarities.size() > 0)
			{
				AssigneeTimeResolve assigneeTimeResolve = getSingleIssueResolvingTimePrediction(
						assigneeIssueSimilarities, assignee);
				if (assigneeTimeResolve != null)
				{
					result.add(assigneeTimeResolve);
				}
			}
		}
		return result;
	}

	private AssigneeTimeResolve getSingleIssueResolvingTimePrediction(
			List<JiraIssueSimilarity> assigneeIssueSimilarities, Assignee assignee)
	{
		int selectedIssues = 0;
		Collections.sort(assigneeIssueSimilarities);
		boolean checker = false;
		AssigneeTopSimilarities assigneeTopSimilarities = new AssigneeTopSimilarities(MAX_RESULTS);

		for (int i = 0; i < assigneeIssueSimilarities.size() && !checker; i++)
		{
			JiraIssueSimilarity jiraIssueSimilarity = assigneeIssueSimilarities.get(i);

			if (checkConditions(jiraIssueSimilarity))
			{
				assigneeTopSimilarities.addJiraIssueSimilarity(jiraIssueSimilarity);
				selectedIssues++;
				checker = checkIfIsEnough(selectedIssues);
			}
		}
		if (selectedIssues > 0)
		{
			AssigneeTimeResolve assigneeTimeResolve = new AssigneeTimeResolve();
			assigneeTimeResolve.setAssignee(assignee);
			assigneeTimeResolve.setPredictedTime(helper.getIssuesResolvingTime(assigneeTopSimilarities));

			return assigneeTimeResolve;
		}
		return null;
	}

	private boolean checkConditions(JiraIssueSimilarity jiraIssueSimilarity)
	{
		return jiraIssueSimilarity.getSimilarityLevel() >= MIN_ALPHA
				&& jiraIssueSimilarity.getJiraIssue().getCreatedAt() != null;
	}

	private boolean checkIfIsEnough(int selectedIssues)
	{
		return selectedIssues == MAX_RESULTS ? true : false;
	}
}
