package main;

import java.util.List;

import database.application.DatabaseApplication;
import database.entity.AssignedIssue;
import database.entity.JiraIssue;
import filter.AssigneeFilter;
import filter.custom.AnalyzedIssueFilter;
import filter.custom.MinimumIssueDescripionSizeFilter;
import filter.custom.MinimumIssueFilter;
import filter.custom.TimeStampsNotNullFilter;
import filter.custom.lists.SelectedIssuePriorityFilter;
import filter.custom.lists.SelectedIssueTypesFilter;
import inspection.ResultsInspection;
import jaccard.JaccardTextsSimilarity;
import jira.AssigneeIssueSimilarity;
import jira.AssigneeIssues;
import jira.AssigneeTimeResolve;
import jira.IssueResolveTimePredictable;
import jira.IssuesFilter;
import jira.IssuesSimilarity;
import jira.prediction.PredictionPrintable;
import jira.project.ProjectData;
import lucene.CosineTextsSimilarity;
import prediction.IssueResolveTimePredicter;
import printer.PredictionTextComposer;
import results.JiraIssueWithPredictedTimeToResolve;
import results.ResultInspectable;
import retriever.internet.IssueDownloaderMain;
import retriever.project.ProjectRetriever;
import similarity.IssuesSimilarityCalculator;
import utils.properties.PropertiesReader;
import utils.properties.Property;
import utils.time.ResolveTimeCalculator;

public class PredictionModelViewer
{

	private PropertiesReader propertiesReader;
	private JiraIssue issueFromDb;
	private IssuesFilter issuesFilter;
	private IssuesSimilarity issuesSimilarity;
	private DatabaseApplication databaseApplication;
	private PredictionPrintable predictionPrintable;
	private IssueResolveTimePredictable issueResolveTimePredictable;
	private ResultInspectable resultInspectable;

	public PredictionModelViewer(PropertiesReader propertiesReader)
	{
		this.propertiesReader = propertiesReader;
	}

	public void init()
	{
		new IssueDownloaderMain(propertiesReader).retrieveIssuesFromProjectWithRespectToPropertyFlag(getProjectData(propertiesReader));
		databaseApplication = new DatabaseApplication(propertiesReader);
		issueFromDb = getJiraIssueFromDb(propertiesReader.getAsInt(Property.PROJECT_ID_JIRA_ISSUE_TO_ANALYZE));
		issuesFilter = getIssuesFilter();
		issuesSimilarity = getIssuesSimilarity();
		predictionPrintable = getPredictionPrinter();
		issueResolveTimePredictable = getIssueResolveTimePredictable();
		resultInspectable = new ResultsInspection();
	}

	private ProjectData getProjectData(PropertiesReader propertiesReader)
	{
		return new ProjectRetriever(propertiesReader).getProjectFromProperties();
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
				.addFilter(new MinimumIssueFilter()).addFilter(new SelectedIssueTypesFilter())
				.addFilter(new SelectedIssuePriorityFilter()).addFilter(new AnalyzedIssueFilter());
	}

	private IssueResolveTimePredictable getIssueResolveTimePredictable()
	{
		return new IssueResolveTimePredicter();
	}

	private IssuesSimilarity getIssuesSimilarity()
	{
		return new IssuesSimilarityCalculator(propertiesReader, new JaccardTextsSimilarity(propertiesReader), new CosineTextsSimilarity());
	}

	private JiraIssue getJiraIssueFromDb(int jiraIssueId)
	{
		DatabaseApplication dba = new DatabaseApplication(propertiesReader);
		return dba.getJiraIssue(jiraIssueId);
	}

	public void showPrediction()
	{
		List<AssigneeIssues> assigneesAndTheirIssues = issuesFilter.getAssignedIssues(issueFromDb.getJiraProject());
		AssignedIssue assignedIssue = issueFromDb.getAssignedIssues().iterator().next();
		for (AssigneeIssues assigneeIssues : assigneesAndTheirIssues)
		{
			showPredictionForAssignee(assigneeIssues, assignedIssue);
		}
		printRealData(assignedIssue);
	}

	private void printRealData(AssignedIssue assignedIssue)
	{
		predictionPrintable.print("Real time: " + ResolveTimeCalculator.getResolveTime(assignedIssue));
		predictionPrintable.print("Real assignee: " + assignedIssue.getAssignee().getName());
	}

	private void showPredictionForAssignee(AssigneeIssues assigneeIssues, AssignedIssue assignedIssue)
	{
		AssigneeIssueSimilarity assigneesWithIssueSimilarities = issuesSimilarity.getAssigneesWithIssueSimilarities(assigneeIssues,
				issueFromDb);
		AssigneeTimeResolve prediction = issueResolveTimePredictable.getPrediction(assigneesWithIssueSimilarities);
		double meanSquaredError = resultInspectable.getMeanSquaredError(new JiraIssueWithPredictedTimeToResolve(assignedIssue, prediction));
		predictionPrintable.printPrediction(prediction, meanSquaredError);
	}

}
