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
		for (JiraIssueWithPredictedTimeToResolve jiraIssueWithPredictedTimeToResolve : jiraIssueWithPredictedTimeToResolves)
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
		double predictedValues[] = getPredictedValues(jiraIssueWithPredictedTimeToResolves);
		double realTimeResolve[] = getRealTimeResolve(jiraIssueWithPredictedTimeToResolves);
		double mean = getMean(realTimeResolve);
		return getSumOfDifferencesBetweenPredictedAndMean(predictedValues, mean)
				/ getSumOfDifferencesBetweenRealTimeAndMean(realTimeResolve, mean);
	}

	private double getSumOfDifferencesBetweenRealTimeAndMean(double[] realTimeResolve, double mean)
	{
		return getSumOfPoweredDifferences(realTimeResolve, mean);
	}

	private double getSumOfPoweredDifferences(double[] realTimeResolve, double mean)
	{
		double sum = 0;
		for (int i = 0; i < realTimeResolve.length; i++)
		{
			sum += getPoweredDifference(realTimeResolve[i], mean);
		}
		return sum / realTimeResolve.length;
	}

	private double getSumOfDifferencesBetweenPredictedAndMean(double[] predictedValues, double mean)
	{
		return getSumOfPoweredDifferences(predictedValues, mean);
	}

	private double getPoweredDifference(double value1, double value2)
	{
		return Math.pow(value1 - value2, 2);
	}

	private double getMean(double[] values)
	{
		double mean = 0;
		for (double value : values)
		{
			mean += value;
		}
		return mean / values.length;
	}

	private double[] getRealTimeResolve(List<JiraIssueWithPredictedTimeToResolve> jiraIssueWithPredictedTimeToResolves)
	{
		double values[] = new double[jiraIssueWithPredictedTimeToResolves.size()];
		for (int i = 0; i < jiraIssueWithPredictedTimeToResolves.size(); i++)
		{
			JiraIssueWithPredictedTimeToResolve jiraIssue = jiraIssueWithPredictedTimeToResolves.get(i);
			values[i] = getRealTimeJiraIssueResolve(jiraIssue.getJiraIssue());
		}
		return values;
	}

	private double[] getPredictedValues(List<JiraIssueWithPredictedTimeToResolve> jiraIssueWithPredictedTimeToResolves)
	{
		double values[] = new double[jiraIssueWithPredictedTimeToResolves.size()];
		for (int i = 0; i < jiraIssueWithPredictedTimeToResolves.size(); i++)
		{
			JiraIssueWithPredictedTimeToResolve jiraIssue = jiraIssueWithPredictedTimeToResolves.get(i);
			values[i] = jiraIssue.getAssigneeTimeResolve().getPredictedTime();
		}
		return values;
	}

}
