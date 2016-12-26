package inspection;

import java.sql.Timestamp;
import java.util.List;

import database.entity.AssignedIssue;
import results.JiraIssueWithPredictedTimeToResolve;
import results.ResultInspectable;
import utils.converter.TimestampConverter;

public class ResultsInspection implements ResultInspectable
{

	@Override
	public double getMeanSquaredError(JiraIssueWithPredictedTimeToResolve jiraIssueWithPredictedTime)
	{
		double realTimeJiraIssueResolve = getRealTimeJiraIssueResolve(jiraIssueWithPredictedTime.getJiraIssue());
		double predictedTime = jiraIssueWithPredictedTime.getAssigneeTimeResolve().getPredictedTime();
		return realTimeJiraIssueResolve - predictedTime;
	}

	private double getRealTimeJiraIssueResolve(AssignedIssue assignedIssue)
	{
		Timestamp resolvedAt = assignedIssue.getResolvedAt();
		Timestamp startAt = assignedIssue.getJiraIssue().getCreatedAt();
		return TimestampConverter.getDifferenceInDays(resolvedAt, startAt);
	}

	@Override
	public double getRootMeanSquaredError(List<JiraIssueWithPredictedTimeToResolve> jiraIssueWithPredictedTimeToResolves)
	{
		double meanSquaredErrorResult = 0;
		for(JiraIssueWithPredictedTimeToResolve jiraIssueWithPredictedTimeToResolve : jiraIssueWithPredictedTimeToResolves)
		{
			double meanSquaredError = getMeanSquaredError(jiraIssueWithPredictedTimeToResolve);
			meanSquaredErrorResult += Math.pow(meanSquaredError, 2);
		}
		meanSquaredErrorResult /= jiraIssueWithPredictedTimeToResolves.size();
		return Math.sqrt(meanSquaredErrorResult);
	}

	@Override
	public double getCoefficientOfDetermination(List<JiraIssueWithPredictedTimeToResolve> jiraIssueWithPredictedTimeToResolves)
	{
		throw new UnsupportedOperationException();
	}

}
