package prediction;

import java.util.ArrayList;
import java.util.List;

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
	public List<AssigneeTimeResolve> getPrediction(List<AssigneeIssueSimilarity> assigneesIssuesSimilarities)
	{
		List<AssigneeTimeResolve> list = new ArrayList<>();
		for (AssigneeIssueSimilarity ais : assigneesIssuesSimilarities)
		{
			double predictedIssueResolvedTime = issueResolvingTimeCalculator.getIssuesResolvingTime(ais.getAssignedJiraIssues());
			list.add(new AssigneeTimeResolve(ais.getAssignee(), predictedIssueResolvedTime));
		}
		return list;
	}
}
