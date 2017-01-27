package inspection;

import java.sql.Timestamp;
import java.util.List;

import database.entity.AssignedIssue;
import results.JiraIssueWithPredictedTimeToResolve;
import results.ResultInspectable;
import utils.converter.TimestampConverter;

public class ResultsInspection implements ResultInspectable
{

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getRootMeanSquaredError(List<JiraIssueWithPredictedTimeToResolve> jiraIssueWithPredictedTimeToResolves)
	{
		List<JiraIssueWithPredictedTimeToResolve> jiraIssuesWithPositivePrediction = PredictionTimeChecker
				.getAssigneesWithCorrectPredictedTime(jiraIssueWithPredictedTimeToResolves);
		if (jiraIssuesWithPositivePrediction.isEmpty())
		{
			return -1;
		}
		double meanSquaredErrorResult = 0;
		for (JiraIssueWithPredictedTimeToResolve jiraIssueWithPredictedTimeToResolve : jiraIssuesWithPositivePrediction)
		{
			double meanSquaredError = getMeanSquaredError(jiraIssueWithPredictedTimeToResolve);
			meanSquaredErrorResult += Math.pow(meanSquaredError, 2);
		}
		meanSquaredErrorResult /= jiraIssuesWithPositivePrediction.size();
		return Math.sqrt(meanSquaredErrorResult);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getCoefficientOfDetermination(List<JiraIssueWithPredictedTimeToResolve> jiraIssueWithPredictedTimeToResolves)
	{
		List<JiraIssueWithPredictedTimeToResolve> jiraIssuesWithPositivePrediction = PredictionTimeChecker
				.getAssigneesWithCorrectPredictedTime(jiraIssueWithPredictedTimeToResolves);
		if (jiraIssuesWithPositivePrediction.isEmpty())
		{
			return -1;
		}
		double predictedValues[] = getPredictedValues(jiraIssuesWithPositivePrediction);
		double realTimeResolve[] = getRealTimeResolve(jiraIssuesWithPositivePrediction);
		double mean = getMean(predictedValues);
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
		for (double value : realTimeResolve)
		{
			sum += getPoweredDifference(value, mean);
		}
		return sum;
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
