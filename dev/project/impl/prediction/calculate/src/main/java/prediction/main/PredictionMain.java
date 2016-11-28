package prediction.main;

import java.util.List;

import jira.AssigneeTimeResolve;
import jira.JiraIssueSimilarity;
import jira.KnnResolvable;
import prediction.IssueResolvingTimes;

public class PredictionMain implements KnnResolvable
{

	@Override
	public List<AssigneeTimeResolve> getPrediction(List<JiraIssueSimilarity> jiraIssueSimilarities)
	{
		IssueResolvingTimes issueResolvingTimes = new IssueResolvingTimes();
		return issueResolvingTimes.getIssueResolvingTimePrediction(jiraIssueSimilarities);
	}

}
