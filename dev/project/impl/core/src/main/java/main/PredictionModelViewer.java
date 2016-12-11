package main;

import java.util.List;

import database.application.DatabaseApplication;
import database.entity.JiraIssue;
import filter.AssigneeFilter;
import filter.MinimumIssueFilter;
import jira.AssigneeIssueSimilarity;
import jira.AssigneeIssues;
import jira.AssigneeTimeResolve;
import jira.IssueResolveTimePredictable;
import jira.IssuesFilter;
import jira.IssuesSimilarity;
import jira.prediction.PredictionPrintable;
import lucene.CosineTextsSimilarity;
import prediction.IssueResolveTimePredicter;
import printer.PredictionTextComposer;
import retriever.internet.IssueDownloaderMain;
import similarity.IssuesSimilarityCalculator;
import utils.properties.PropertiesReader;

public class PredictionModelViewer
{

	private PropertiesReader propertiesReader;
	private JiraIssue issueFromDb;
	private IssuesFilter issuesFilter;
	private IssuesSimilarity issuesSimilarity;
	private DatabaseApplication databaseApplication;
	private PredictionPrintable predictionPrintable;
	private IssueResolveTimePredictable issueResolveTimePredictable;

	public PredictionModelViewer(PropertiesReader propertiesReader)
	{
		this.propertiesReader = propertiesReader;
	}

	public void init()
	{
		new IssueDownloaderMain(propertiesReader).retrieveAllIssuesWithRespectToPropertyFlag();
		databaseApplication = new DatabaseApplication(propertiesReader);
		issueFromDb = getJiraIssueFromDb();
		issuesFilter = getIssuesFilter();
		issuesSimilarity = getIssuesSimilarity(databaseApplication);
		predictionPrintable = getPredictionPrinter();
		issueResolveTimePredictable = getIssueResolveTimePredictable();
	}

	private PredictionPrintable getPredictionPrinter()
	{
		return new PredictionTextComposer(propertiesReader);
	}

	private IssuesFilter getIssuesFilter()
	{
		AssigneeFilter assigneeFilter = new AssigneeFilter(propertiesReader, databaseApplication);
		assigneeFilter.init();
		return assigneeFilter.addFilter(new MinimumIssueFilter());
	}

	private IssueResolveTimePredictable getIssueResolveTimePredictable()
	{
		return new IssueResolveTimePredicter();
	}

	private IssuesSimilarity getIssuesSimilarity(DatabaseApplication da)
	{
		return new IssuesSimilarityCalculator(propertiesReader, da, new CosineTextsSimilarity());
	}

	private JiraIssue getJiraIssueFromDb()
	{
		DatabaseApplication dba = new DatabaseApplication(propertiesReader);
		return dba.getJiraIssue(1);
	}

	public void showPrediction()
	{
		List<AssigneeIssues> assigneesAndTheirIssues = issuesFilter.getAssignedIssues();
		List<AssigneeIssueSimilarity> assigneesWithIssueSimilarities = issuesSimilarity
				.getAssigneesWithIssueSimilarities(assigneesAndTheirIssues, issueFromDb);
		List<AssigneeTimeResolve> prediction = issueResolveTimePredictable.getPrediction(assigneesWithIssueSimilarities);
		predictionPrintable.getPrediction(issueFromDb, prediction);
	}

}
