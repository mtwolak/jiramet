package similarity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import database.application.DatabaseApplication;
import database.entity.JiraIssue;
import database.entity.JiraProject;
import jira.AssigneeIssueSimilarity;
import jira.AssigneeIssues;
import jira.IssuesSimilarity;
import jira.JiraIssueSimilarity;
import lucene.CosineTextsSimilarity;
import utils.properties.PropertiesReader;
import utils.properties.Property;

public class IssuesSimilarityCalculator implements IssuesSimilarity  
{
	private IssuesSimilarityHelper issuesSimilarityHelper;
	private DatabaseApplication dba;
	private CosineTextsSimilarity cts;
	private TextSimilarity textsSimilarity;
	private PropertiesReader propertiesReader;
	private static final Logger LOGGER = Logger.getLogger(IssuesSimilarityCalculator.class.getName());

	public IssuesSimilarityCalculator(PropertiesReader propertiesReader, DatabaseApplication databaseApplication, TextSimilarity textsSimilarityStrategy)
	{
		this.propertiesReader = propertiesReader;
		this.dba = databaseApplication;
		this.textsSimilarity = textsSimilarityStrategy;
	}
	
	public void init() {
		this.issuesSimilarityHelper = getIssueSimilarityHelper();
	}

	@Override
	public List<AssigneeIssueSimilarity> getAssigneesWithIssueSimilarities(List<AssigneeIssues> assigneeIssues,
			JiraIssue jiraIssueToCompare)
	{
		// TODO Auto-generated method stub
		return null;
	}

	// @Override
	// public List<JiraIssueSimilarity> getIssuesSimilarityList(JiraIssue
	// jiraIssue)
	// {
	// List<JiraIssueSimilarity> similarityList = new
	// ArrayList<JiraIssueSimilarity>();
	// JiraProject project = jiraIssue.getJiraProject();
	// @SuppressWarnings("unchecked")
	// List<JiraIssue> issues = dba.getJiraIssues(project);
	// for (JiraIssue issue : issues)
	// {
	// if (issue.getJiraIssueId() != jiraIssue.getJiraIssueId())
	// similarityList.add(new JiraIssueSimilarity(issue,
	// getIssuesSimilarity(jiraIssue, issue)));
	// }
	// dba.closeSession();
	//
	// return similarityList;
	// }

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
	

}
