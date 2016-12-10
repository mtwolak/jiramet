package similarity;

import java.util.ArrayList;
import java.util.List;

import database.application.DatabaseApplication;
import database.entity.JiraIssue;
import jira.AssigneeIssueSimilarity;
import jira.AssigneeIssues;
import jira.IssuesSimilarity;
import jira.JiraIssueSimilarity;
import utils.properties.PropertiesReader;
import utils.properties.Property;

public class IssuesSimilarityCalculator implements IssuesSimilarity  
{
	private IssuesSimilarityCommentsCollector issuesSimilarityCommentsCollector;
	private DatabaseApplication dba;
	private TextSimilarity textsSimilarity;
	private PropertiesReader propertiesReader;

	public IssuesSimilarityCalculator(PropertiesReader propertiesReader, DatabaseApplication databaseApplication, TextSimilarity textsSimilarityStrategy)
	{
		this.propertiesReader = propertiesReader;
		this.dba = databaseApplication;
		this.textsSimilarity = textsSimilarityStrategy;
		init();
	}
	
	public void init() {
		this.issuesSimilarityCommentsCollector = getIssuesSimilarityCommentsCollector();
	}

	public double getIssuesSimilarity(JiraIssue issue1, JiraIssue issue2)
	{
		return propertiesReader.getAsDouble(Property.SUMMARY_WEIGHT) * calculateSimilarity(issue1.getSummary(), issue2.getSummary())
				+ propertiesReader.getAsDouble(Property.DESCRIPTION_WEIGHT)
				* calculateSimilarity(issue1.getDescription(), issue2.getDescription())
				+ calculateCommentsSimilarity(issue1, issue2);
	}
	
	private double calculateCommentsSimilarity(JiraIssue issue1, JiraIssue issue2)
	{
		if(checkForIssueComments(issue2))
			return propertiesReader.getAsDouble(Property.COMMENTS_WEIGHT)
					* calculateSimilarity(issue1.getSummary(), issuesSimilarityCommentsCollector.collectIssueComments(issue2).toString());
		else 
			return 0.0;
	}
	
	private boolean checkForAllRequiredTexts(JiraIssue issue1, JiraIssue issue2)
	{
		return ((issue1.getSummary() != null) && (issue1.getDescription() != null) && (issue2.getSummary() != null) && (issue2.getDescription() != null));
	}
	
	private boolean checkForIssueComments(JiraIssue issue)
	{
		return !issuesSimilarityCommentsCollector.collectIssueComments(issue).toString().equals("");
	}

	private double calculateSimilarity(String text1, String text2)
	{
		return textsSimilarity.getSimilarity(text1, text2);
	}

	protected IssuesSimilarityCommentsCollector getIssuesSimilarityCommentsCollector()
	{
		return new IssuesSimilarityCommentsCollector();
	}

	@Override
	public List<AssigneeIssueSimilarity> getAssigneesWithIssueSimilarities(List<AssigneeIssues> assigneeIssues,
			JiraIssue newJiraIssue)
	{
		List<AssigneeIssueSimilarity> assigneeSimilarityList = new ArrayList<AssigneeIssueSimilarity>();
		List<JiraIssueSimilarity> jiraIssueSimilarities = new ArrayList<JiraIssueSimilarity>();

		for(AssigneeIssues asi : assigneeIssues)
		{
			for(JiraIssue issue : asi.getAssignedJiraIssues())
			{
				if (issue.getJiraIssueId() != newJiraIssue.getJiraIssueId() && (checkForAllRequiredTexts(newJiraIssue, issue)))
				{
					System.out.println("NEXT**********************");
					System.out.println("asi "+asi.toString());
				    System.out.println("iss "+issue.getJiraIssueId());
				    System.out.println("sim "+getIssuesSimilarity(newJiraIssue, issue));
				    System.out.println(jiraIssueSimilarities == null);
					jiraIssueSimilarities.add(new JiraIssueSimilarity(issue, getIssuesSimilarity(newJiraIssue, issue)));
				}
			}
			System.out.println("--------------------------------------------------------------------------");
			assigneeSimilarityList.add(new AssigneeIssueSimilarity(asi.getAssignee(), jiraIssueSimilarities));
			jiraIssueSimilarities = null;
		}
		dba.closeSession();
		
		return assigneeSimilarityList;
	}
	

}
