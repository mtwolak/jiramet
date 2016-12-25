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
		return TimestampConverter.getDifference(resolvedAt, startAt);
	}

	@Override
	public double getRootMeanSquaredError(List<JiraIssueWithPredictedTimeToResolve> jiraIssueWithPredictedTimeToResolves)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public double getCoefficientOfDetermination(List<JiraIssueWithPredictedTimeToResolve> jiraIssueWithPredictedTimeToResolves)
	{
		throw new UnsupportedOperationException();
	}

}
