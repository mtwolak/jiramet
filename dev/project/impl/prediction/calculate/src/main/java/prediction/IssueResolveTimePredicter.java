package prediction;

import jira.AssigneeIssueSimilarity;
import jira.AssigneeTimeResolve;
import jira.IssueResolveTimePredictable;

public class IssueResolveTimePredicter implements IssueResolveTimePredictable
{

	private IssueResolvingTimeCalculator issueResolvingTimeCalculator;

	public IssueResolveTimePredicter()
	{
		this.issueResolvingTimeCalculator = new IssueResolvingTimeCalculator();
	}

	@Override
	public AssigneeTimeResolve getPrediction(AssigneeIssueSimilarity assigneesIssuesSimilarities)
	{
		double predictedIssueResolvedTime = issueResolvingTimeCalculator.getIssuesResolvingTime(assigneesIssuesSimilarities.getAssignedJiraIssues());
		return new AssigneeTimeResolve(assigneesIssuesSimilarities.getAssignee(), predictedIssueResolvedTime);
	}
}
