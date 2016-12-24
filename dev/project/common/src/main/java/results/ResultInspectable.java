package results;

import java.util.List;

public interface ResultInspectable
{
	double getMeanSquaredError(JiraIssueWithPredictedTimeToResolve predictedTimeResolveWithJira);

	double getRootMeanSquaredError(List<JiraIssueWithPredictedTimeToResolve> jiraIssueWithPredictedTimeToResolves);

	double getCoefficientOfDetermination(List<JiraIssueWithPredictedTimeToResolve> jiraIssueWithPredictedTimeToResolves);
}
