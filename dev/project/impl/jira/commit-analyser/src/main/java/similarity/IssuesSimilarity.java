package similarity;

import java.util.ArrayList;
import java.util.List;

import database.application.DatabaseApplication;
import database.entity.JiraIssue;
import database.entity.JiraProject;
import javafx.util.Pair;
import lucene.CosineTextSimilarity;

public class IssuesSimilarity 
{
	static DatabaseApplication dba = new DatabaseApplication();
	static List<Pair<Integer, Double>> similarities;
	CosineTextSimilarity cts;
	
	public static void main(String[] args)
	{
		JiraIssue issue = dba.getJiraIssue(1);
		System.out.println(issue);
		getIssueSimilarityList(issue);
	}
	
	public static ArrayList<Pair<Integer, Double>> getIssueSimilarityList(JiraIssue issue)
	{
		dba = new DatabaseApplication();
		similarities = new ArrayList<Pair<Integer, Double>>();
		JiraProject project = issue.getJiraProject();
		List<JiraIssue> issues = dba.getJiraIssues(project);
		for(JiraIssue jissue : issues)
        {
			similarities.add(new Pair<Integer, Double>(jissue.getJiraIssueId(), getIssueSimilarity(issue, jissue)));
        }
		dba.closeSession();
		
		return null;
	}	
	
	public static double getIssueSimilarity(JiraIssue issue1, JiraIssue issue2)
	{
		
		return -1.0;
	}
}
