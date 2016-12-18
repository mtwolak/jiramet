package main;

import java.util.List;

import database.application.DatabaseApplication;
import database.entity.JiraIssue;
import filter.AssigneeFilter;
import filter.custom.MinimumIssueDescripionSizeFilter;
import filter.custom.MinimumIssueFilter;
import filter.custom.TimeStampsNotNullFilter;
import filter.custom.lists.SelectedIssuePriorityFilter;
import filter.custom.lists.SelectedIssueTypesFilter;
import jaccard.JaccardTextsSimilarity;
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
import utils.Timer;
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
		issuesSimilarity = getIssuesSimilarity();
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
		return assigneeFilter.addFilter(new TimeStampsNotNullFilter()).addFilter(new MinimumIssueDescripionSizeFilter())
				.addFilter(new MinimumIssueFilter()).addFilter(new SelectedIssueTypesFilter()).addFilter(new SelectedIssuePriorityFilter());
	}

	private IssueResolveTimePredictable getIssueResolveTimePredictable()
	{
		return new IssueResolveTimePredicter();
	}

	private IssuesSimilarity getIssuesSimilarity()
	{
		return new IssuesSimilarityCalculator(propertiesReader, new JaccardTextsSimilarity(propertiesReader), new CosineTextsSimilarity());
	}

	private JiraIssue getJiraIssueFromDb()
	{
		DatabaseApplication dba = new DatabaseApplication(propertiesReader);
		return dba.getJiraIssue(1);
	}

	public void showPrediction()
	{
		List<AssigneeIssues> assigneesAndTheirIssues = issuesFilter.getAssignedIssues(issueFromDb.getJiraProject());
		Timer t = new Timer();
		for (AssigneeIssues assigneeIssues : assigneesAndTheirIssues)
		{
			showPredictionForAssignee(t, assigneeIssues);
		}
	}

	private void showPredictionForAssignee(Timer t, AssigneeIssues assigneeIssues)
	{
		t.checkpoint();
		AssigneeIssueSimilarity assigneesWithIssueSimilarities = issuesSimilarity.getAssigneesWithIssueSimilarities(assigneeIssues,
				issueFromDb);
		AssigneeTimeResolve prediction = issueResolveTimePredictable.getPrediction(assigneesWithIssueSimilarities);
		predictionPrintable.printPrediction(issueFromDb, prediction);
	}

}
