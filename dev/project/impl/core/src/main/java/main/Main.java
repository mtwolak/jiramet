package main;

import java.util.List;

import database.application.DatabaseApplication;
import database.entity.JiraIssue;
import jira.AssigneeTimeResolve;
import jira.JiraIssueSimilarity;
import jira.JiraWebLogDownloader;
import jira.KnnResolvable;
import jira.prediction.PredictionPrintable;
import prediction.main.PredictionMain;
import printer.PredictionTextComposer;
import retriever.main.IssueDownloaderMain;
import similarity.IssuesSimilarityCalculator;

public class Main {
	
	//cały main
	public static void main(String args[]) {
//		new IssueDownloaderMain().retrieveAllIssues();
		JiraIssue issue = getJiraIssueFromDb();
		System.out.println(issue.getSummary());
		List<JiraIssueSimilarity> issuesSimilarityList = new IssuesSimilarityCalculator().getIssuesSimilarityList(issue);
		KnnResolvable knn = new PredictionMain();
		List<AssigneeTimeResolve> prediction = knn.getPrediction(issuesSimilarityList);
		PredictionPrintable print = new PredictionTextComposer();
		print.printPrediction(issue, prediction);

	}

	private static JiraIssue getJiraIssueFromDb()
	{
		DatabaseApplication dba = new DatabaseApplication();
		return dba.getJiraIssue(1);
	}

}
