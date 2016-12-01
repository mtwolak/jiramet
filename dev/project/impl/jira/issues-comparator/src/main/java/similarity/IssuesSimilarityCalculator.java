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

public class IssuesSimilarityCalculator implements IssuesSimilarity
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
		return propertiesReader.getAsDouble(Property.SUMMARY_WEIGHT) * getIssuesSummariesSimilarity(issue1, issue2)
				+ propertiesReader.getAsDouble(Property.DESCRIPTION_WEIGHT) * getIssuesDescriptionsSimilarity(issue1, issue2)
				+ propertiesReader.getAsDouble(Property.COMMENTS_WEIGHT) * getIssuesCommentsSimilarity(issue1, issue2);
	}

	public double getIssuesSummariesSimilarity(JiraIssue issue1, JiraIssue issue2)
	{
		similarity = 0;
		try
		{
			cts = new CosineTextsSimilarity(issue1.getSummary(), issue2.getSummary());
			similarity = cts.getSimilarity();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return similarity;
	}

	public double getIssuesDescriptionsSimilarity(JiraIssue issue1, JiraIssue issue2)
	{
		similarity = 0;
		String desc1 = issue1.getDescription();
		String desc2 = issue2.getDescription();
		if (desc1 != null && desc2 != null)
		{
			similarity = cts.getSimilarity(desc1, desc2);
		}
		return similarity;
	}

	public double getIssuesCommentsSimilarity(JiraIssue issue1, JiraIssue issue2)
	{
		similarity = 0;
		StringBuilder comments1 = ish.collectIssueComments(issue1);
		StringBuilder comments2 = ish.collectIssueComments(issue2);
		if (comments1.length() != 0 && comments2.length() != 0)
		{
			similarity = cts.getSimilarity(comments1.toString(), comments2.toString());
		}
		return similarity;
	}

}
