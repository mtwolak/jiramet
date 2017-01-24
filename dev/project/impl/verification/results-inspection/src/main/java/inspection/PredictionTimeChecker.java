package inspection;

import java.util.ArrayList;
import java.util.List;

import results.JiraIssueWithPredictedTimeToResolve;

public class PredictionTimeChecker
{

	public static List<JiraIssueWithPredictedTimeToResolve> getList(
			List<JiraIssueWithPredictedTimeToResolve> jiraIssueWithPredictedTimeToResolves)
	{
		List<JiraIssueWithPredictedTimeToResolve> list = new ArrayList<>();
		for (JiraIssueWithPredictedTimeToResolve jiraIssue : jiraIssueWithPredictedTimeToResolves)
		{
			if (jiraIssue.getAssigneeTimeResolve().getPredictedTime() > 0)
			{
				list.add(jiraIssue);
			}
		}
		return list;
	}

}
