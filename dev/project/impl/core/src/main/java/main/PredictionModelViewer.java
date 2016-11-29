package main;

import java.util.List;

import database.application.DatabaseApplication;
import database.entity.JiraIssue;
import jira.AssigneeTimeResolve;
import jira.JiraIssueSimilarity;
import jira.KnnResolvable;
import jira.prediction.PredictionPrintable;
import prediction.main.PredictionMain;
import printer.PredictionTextComposer;
import similarity.IssuesSimilarityCalculator;
import utils.properties.PropertiesReader;

public class PredictionModelViewer
{

	private PropertiesReader propertiesReader;

	public PredictionModelViewer(PropertiesReader propertiesReader)
	{
		this.propertiesReader = propertiesReader;
	}

	public void show()
	{
//		new IssueDownloaderMain().retrieveAllIssues();
		JiraIssue issue = getJiraIssueFromDb();
		System.out.println(issue.getSummary());
		List<JiraIssueSimilarity> issuesSimilarityList = new IssuesSimilarityCalculator(propertiesReader)
				.getIssuesSimilarityList(issue);
		KnnResolvable knn = new PredictionMain();
		List<AssigneeTimeResolve> prediction = knn.getPrediction(issuesSimilarityList);
		PredictionPrintable print = new PredictionTextComposer(propertiesReader);
		print.printPrediction(issue, prediction);

	}

	private JiraIssue getJiraIssueFromDb()
	{
		DatabaseApplication dba = new DatabaseApplication(propertiesReader);
		return dba.getJiraIssue(1);
	}

}
