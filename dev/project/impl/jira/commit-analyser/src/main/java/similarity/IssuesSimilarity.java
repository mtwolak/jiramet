package similarity;

import java.util.List;

import database.application.DatabaseApplication;
import database.entity.JiraIssue;
import database.entity.JiraProject;
import javafx.util.Pair;

public class IssuesSimilarity 
{
	static DatabaseApplication dba = new DatabaseApplication();
	
	public static void main(String[] args)
	{
		JiraIssue issue = dba.getJiraIssue(1);
		System.out.println(issue);
		getIssueSimilarityList(issue);
	}
	
	public static List<Pair<Integer, Double>> getIssueSimilarityList(JiraIssue issue)
	{
		dba = new DatabaseApplication();
		JiraProject project = issue.getJiraProject();
		List<JiraIssue> issues = dba.getJiraIssues(project);
		
        /*for(JiraIssue ji : issues)
        {
        	System.out.println(ji.getIssueReporter().getFullName());
        }*/
		dba.closeSession();
		
		return null;
	}
	
	public double getIssueSimilarity(int issueID)
	{
		return -1.0;
	}
}
