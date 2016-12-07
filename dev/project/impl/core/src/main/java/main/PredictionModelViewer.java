package main;

import java.util.List;

import database.application.DatabaseApplication;
import database.entity.JiraIssue;
import jira.AssigneeTimeResolve;
import jira.IssuesSimilarity;
import jira.JiraIssueSimilarity;
import jira.KnnResolvable;
import jira.prediction.PredictionPrintable;
import lucene.CosineTextsSimilarity;
import prediction.main.PredictionMain;
import printer.PredictionTextComposer;
import retriever.main.IssueDownloaderMain;
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
//		new IssueDownloaderMain(propertiesReader).retrieveAllIssues();
		
//		
//		JiraIssue issue = getJiraIssueFromDb();
//		System.out.println(issue.getSummary());
//		IssuesSimilarity issuesSimilarity = getIssuesSimilarity();
//		List<JiraIssueSimilarity> issuesSimilarityList = issuesSimilarity.getIssuesSimilarityList(issue);
//		KnnResolvable knn = getKnn();
//		List<AssigneeTimeResolve> prediction = knn.getPrediction(issuesSimilarityList);
//		PredictionPrintable print = new PredictionTextComposer(propertiesReader);
//		print.printPrediction(issue, prediction);

	}

	private KnnResolvable getKnn()
	{
		return new PredictionMain(propertiesReader);
	}

	private IssuesSimilarity getIssuesSimilarity()
	{
		return new IssuesSimilarityCalculator(propertiesReader, new DatabaseApplication(propertiesReader), new CosineTextsSimilarity());
	}

	private JiraIssue getJiraIssueFromDb()
	{
		DatabaseApplication dba = new DatabaseApplication(propertiesReader);
		return dba.getJiraIssue(1);
	}

}
