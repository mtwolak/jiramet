package main;

import java.util.List;

import database.entity.JiraIssue;
import jira.AssigneeTimeResolve;
import jira.JiraIssueSimilarity;
import jira.JiraWebLogDownloader;
import jira.KnnResolvable;
import jira.prediction.PredictionPrintable;
import printer.PredictionTextComposer;
import retriever.main.IssueDownloaderMain;
import similarity.IssuesSimilarityCalculator;

public class Main {
	
	//cały main
	public static void main(String args[]) {
//		new IssueDownloaderMain().retrieveAllIssues();
//		JiraIssue issue = getJiraIssueFromDb();
//		List<JiraIssueSimilarity> issuesSimilarityList = new IssuesSimilarityCalculator().getIssuesSimilarityList(issue);
//		KnnResolvable knn = (KnnResolvable) new Object();
//		List<AssigneeTimeResolve> prediction = knn.getPrediction(issuesSimilarityList);
//		PredictionPrintable print = new PredictionTextComposer();
//		print.printPrediction(issue, prediction);

	}

	private static JiraIssue getJiraIssueFromDb()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
