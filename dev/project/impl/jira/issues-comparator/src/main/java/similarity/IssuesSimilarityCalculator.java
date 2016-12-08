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
	private IssuesSimilarityHelper issuesSimilarityHelper;
	private DatabaseApplication dba;
	private TextSimilarity textsSimilarity;
	private PropertiesReader propertiesReader;

	public IssuesSimilarityCalculator(PropertiesReader propertiesReader, DatabaseApplication databaseApplication, TextSimilarity textsSimilarityStrategy)
	{
		this.propertiesReader = propertiesReader;
		this.dba = databaseApplication;
		this.textsSimilarity = textsSimilarityStrategy;
	}
	
	public void init() {
		this.issuesSimilarityHelper = getIssueSimilarityHelper();
	}

	public double getIssuesSimilarity(JiraIssue issue1, JiraIssue issue2)
	{
		return propertiesReader.getAsDouble(Property.SUMMARY_WEIGHT) * calculateSimilarity(issue1.getSummary(), issue2.getSummary())
				+ propertiesReader.getAsDouble(Property.DESCRIPTION_WEIGHT)
						* calculateSimilarity(issue1.getDescription(), issue2.getDescription())
				+ propertiesReader.getAsDouble(Property.COMMENTS_WEIGHT)
						* calculateSimilarity(issue1.getSummary(), issuesSimilarityHelper.collectIssueComments(issue2).toString());
	}

	private double calculateSimilarity(String text1, String text2)
	{
		return textsSimilarity.getSimilarity(text1, text2);
	}

	protected IssuesSimilarityHelper getIssueSimilarityHelper()
	{
		return new IssuesSimilarityHelper();
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
				if (issue.getJiraIssueId() != newJiraIssue.getJiraIssueId())
					jiraIssueSimilarities.add(new JiraIssueSimilarity(issue, getIssuesSimilarity(newJiraIssue, issue)));
			}
			assigneeSimilarityList.add(new AssigneeIssueSimilarity(asi.getAssignee(), jiraIssueSimilarities));
			jiraIssueSimilarities = null;
		}
		dba.closeSession();
		
		return assigneeSimilarityList;
	}
	

}
