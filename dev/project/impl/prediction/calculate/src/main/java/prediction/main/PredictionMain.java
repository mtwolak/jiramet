package prediction.main;

import java.util.List;

import jira.AssigneeTimeResolve;
import jira.JiraIssueSimilarity;
import jira.KnnResolvable;
import prediction.IssueResolvingTimes;
import utils.properties.PropertiesReader;

public class PredictionMain implements KnnResolvable
{

	private PropertiesReader propertiesReader;

	public PredictionMain(PropertiesReader propertiesReader)
	{
		this.propertiesReader = propertiesReader;
	}

	@Override
	public List<AssigneeTimeResolve> getPrediction(List<JiraIssueSimilarity> jiraIssueSimilarities)
	{
		IssueResolvingTimes issueResolvingTimes = new IssueResolvingTimes(propertiesReader);
		return issueResolvingTimes.getIssueResolvingTimePrediction(jiraIssueSimilarities);
	}

}
