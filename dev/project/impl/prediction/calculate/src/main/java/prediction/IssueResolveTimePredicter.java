package prediction;

import jira.AssigneeIssueSimilarity;
import jira.AssigneeTimeResolve;
import jira.IssueResolveTimePredictable;

/**
 * Contains methods that return the predicted time needed to resolve the selected issue by the particular developer
 *
 */
public class IssueResolveTimePredicter implements IssueResolveTimePredictable
{

	private IssueResolvingTimeCalculator issueResolvingTimeCalculator;

	/**
	 * Initializes calculator class
	 */
	public IssueResolveTimePredicter()
	{
		this.issueResolvingTimeCalculator = new IssueResolvingTimeCalculator();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AssigneeTimeResolve getPrediction(AssigneeIssueSimilarity assigneesIssuesSimilarities)
	{
		double predictedIssueResolvedTime = issueResolvingTimeCalculator.getIssuesResolvingTime(assigneesIssuesSimilarities.getAssignedJiraIssues());
		return new AssigneeTimeResolve(assigneesIssuesSimilarities.getAssignee(), predictedIssueResolvedTime);
	}
}
