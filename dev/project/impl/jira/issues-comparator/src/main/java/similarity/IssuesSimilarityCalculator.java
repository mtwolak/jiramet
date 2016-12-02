package similarity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import database.application.DatabaseApplication;
import database.entity.JiraIssue;
import database.entity.JiraProject;
import jira.IssuesSimilarity;
import jira.JiraIssueSimilarity;
import lucene.CosineTextsSimilarity;
import utils.properties.PropertiesReader;
import utils.properties.Property;

public class IssuesSimilarityCalculator implements IssuesSimilarity, TextsSimilarity
{
	private DatabaseApplication dba;
	private IssuesSimilarityHelper ish;
	private CosineTextsSimilarity cts;
	private double similarity;
	private PropertiesReader propertiesReader;

	public IssuesSimilarityCalculator(PropertiesReader propertiesReader)
	{
		this.propertiesReader = propertiesReader;
		this.dba = new DatabaseApplication(propertiesReader);
		this.ish = new IssuesSimilarityHelper();
	}

	@Override
	public List<JiraIssueSimilarity> getIssuesSimilarityList(JiraIssue jiraIssue)
	{
		List<JiraIssueSimilarity> similarityList = new ArrayList<JiraIssueSimilarity>();
		JiraProject project = jiraIssue.getJiraProject();
		@SuppressWarnings("unchecked")
		List<JiraIssue> issues = dba.getJiraIssues(project);
		for (JiraIssue issue : issues)
		{
			if (issue.getJiraIssueId() != jiraIssue.getJiraIssueId())
				similarityList.add(new JiraIssueSimilarity(issue, getIssuesSimilarity(jiraIssue, issue)));
		}
		dba.closeSession();

		return similarityList;
	}

	public double getIssuesSimilarity(JiraIssue issue1, JiraIssue issue2)
	{
		return propertiesReader.getAsDouble(Property.SUMMARY_WEIGHT) * getSimilarity(issue1.getSummary(), issue2.getSummary())
				+ propertiesReader.getAsDouble(Property.DESCRIPTION_WEIGHT) * getSimilarity(issue1.getDescription(), issue2.getDescription())
				+ propertiesReader.getAsDouble(Property.COMMENTS_WEIGHT) * getSimilarity(issue1.getSummary(), ish.collectIssueComments(issue2).toString());
	}

	@Override
	public double getSimilarity(String text1, String text2)
	{
		similarity = 0.0;
		try
		{
			if(text1 != null && text2 != null)
			{
				cts = new CosineTextsSimilarity(text1, text2);
				similarity = cts.getSimilarity();
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return similarity;
	}

}
