package similarity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import database.application.DatabaseApplication;
import database.entity.JiraIssue;
import database.entity.JiraProject;
import jira.IssuesSimilarity;
import jira.JiraIssueSimilarity;
import lucene.CosineTextSimilarity;

public class IssuesSimilarityCalculator implements IssuesSimilarity
{
	private DatabaseApplication dba;
	private IssuesSimilarityParameters isp;
	private IssuesSimilarityHelper ish;
	private CosineTextSimilarity cts;
	private double similarity;

	public IssuesSimilarityCalculator()
	{
		dba = new DatabaseApplication();
		isp = new IssuesSimilarityParameters();
		ish = new IssuesSimilarityHelper();
	}

	public List<JiraIssueSimilarity> getIssuesSimilarityList(JiraIssue jiraIssue) {
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

	public double getIssuesSimilarity(JiraIssue issue1, JiraIssue issue2) {
		return isp.getSummaryWeight() * getIssuesSummariesSimilarity(issue1, issue2)
				+ isp.getDescriptionWeight() * getIssuesDescriptionsSimilarity(issue1, issue2)
				+ isp.getCommentsWeight() * getIssuesCommentsSimilarity(issue1, issue2);
	}

	public double getIssuesSummariesSimilarity(JiraIssue issue1, JiraIssue issue2) {
		similarity = 0;
		try
		{
			similarity = cts.getCosineSimilarity(issue1.getSummary(), issue2.getSummary());
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return similarity;
	}

	public double getIssuesDescriptionsSimilarity(JiraIssue issue1, JiraIssue issue2) {
		similarity = 0;
		String desc1 = issue1.getDescription();
		String desc2 = issue2.getDescription();
		if (desc1 != null && desc2 != null)
		{
			try
			{
				similarity = cts.getCosineSimilarity(desc1, desc2);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return similarity;
	}

	public double getIssuesCommentsSimilarity(JiraIssue issue1, JiraIssue issue2) {
		similarity = 0;
		StringBuilder comments1 = ish.collectIssueComments(issue1);
		StringBuilder comments2 = ish.collectIssueComments(issue2);
		if (comments1.length() != 0 && comments2.length() != 0)
		{
			try
			{
				similarity = cts.getCosineSimilarity(comments1.toString(), comments2.toString());
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return similarity;
	}

}
