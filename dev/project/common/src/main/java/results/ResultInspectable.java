package results;

import java.util.List;

/**
 * Interface for calculating errors
 *
 */
public interface ResultInspectable
{
	/**
	 * Calculates mean squared error
	 * 
	 * @param jiraIssueWithPredictedTime
	 *            jira issue with prediction time to resolve
	 * @return mean squared error
	 */
	double getMeanSquaredError(JiraIssueWithPredictedTimeToResolve jiraIssueWithPredictedTime);

	/**
	 * Calculates root mean squared error
	 * 
	 * @param jiraIssueWithPredictedTimeToResolves
	 *            jira issues with prediction times to resolve
	 * @return root mean squared error
	 */
	double getRootMeanSquaredError(List<JiraIssueWithPredictedTimeToResolve> jiraIssueWithPredictedTimeToResolves);

	/**
	 * Calculates coefficient of determination
	 * 
	 * @param jiraIssueWithPredictedTimeToResolves
	 *            jira issues with prediction times to resolve
	 * @return coefficient of determination
	 */
	double getCoefficientOfDetermination(
			List<JiraIssueWithPredictedTimeToResolve> jiraIssueWithPredictedTimeToResolves);
}
