package inspection;

import java.util.ArrayList;
import java.util.List;

import results.JiraIssueWithPredictedTimeToResolve;

public class PredictionTimeChecker
{

	/**
	 * Method which gives issues with prediction which is greater
	 * than zero. Prediction is less than 0, when there was no data to predict
	 * from
	 * 
	 * @param jiraIssueWithPredictedTimeToResolves Jira issues and their prediction time to resolve
	 * @return List of jira issues with correct prediction time
	 */
	public static List<JiraIssueWithPredictedTimeToResolve> getAssigneesWithCorrectPredictedTime(
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
