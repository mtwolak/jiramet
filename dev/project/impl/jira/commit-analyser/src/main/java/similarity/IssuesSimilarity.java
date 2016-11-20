package similarity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import database.application.DatabaseApplication;
import database.entity.IssueComment;
import database.entity.JiraIssue;
import database.entity.JiraProject;
import javafx.util.Pair;
import lucene.CosineTextSimilarity;

public class IssuesSimilarity 
{
	DatabaseApplication dba;
	
	public IssuesSimilarity(JiraIssue issue)
	{
		dba = new DatabaseApplication();
	}
	
	public List<Pair<Integer, Double>> getIssueSimilarityList(JiraIssue issue)
	{
		List<Pair<Integer, Double>> similarities = new ArrayList<Pair<Integer, Double>>();
		JiraProject project = issue.getJiraProject();
		@SuppressWarnings("unchecked")
		List<JiraIssue> issues = dba.getJiraIssues(project);
		for(JiraIssue jissue : issues)
        {
			if(jissue.getJiraIssueId() != issue.getJiraIssueId())
				similarities.add(new Pair<Integer, Double>(jissue.getJiraIssueId(), getIssuesSimilarity(issue, jissue)));
        }
		dba.closeSession();
		
		return similarities;
	}	
	
	public double getIssuesSimilarity(JiraIssue issue1, JiraIssue issue2)
	{
		return 0.45 * getIssuesSummariesSimilarity(issue1, issue2) 
			   + 0.45 * getIssuesDescriptionsSimilarity(issue1, issue2)
			   + 0.1 * getIssuesCommentsSimilarity(issue1, issue2);    
	}
	
	public double getIssuesSummariesSimilarity(JiraIssue issue1, JiraIssue issue2)
	{
		double summariesSimilarity = 0;
		try {
			summariesSimilarity = CosineTextSimilarity.getCosineSimilarity(issue1.getSummary(), 
					                                                       issue2.getSummary());
		} catch (IOException e) {
			e.printStackTrace();
		}   
		return summariesSimilarity;
	}
	
	public double getIssuesDescriptionsSimilarity(JiraIssue issue1, JiraIssue issue2)
	{
		double descriptionsSimilarity = 0;
	    String desc1 = issue1.getDescription();
		String desc2 = issue2.getDescription();
		if(desc1 != null && desc2 != null)
		{
			try {
			    descriptionsSimilarity = CosineTextSimilarity.getCosineSimilarity(desc1, desc2);
			} catch (IOException e) {
			     e.printStackTrace();
			}
		}   
		return descriptionsSimilarity;
	}
	
	public double getIssuesCommentsSimilarity(JiraIssue issue1, JiraIssue issue2)
	{
		double commentsSimilarity = 0;
		StringBuilder comments1 = collectIssueComments(issue1);
		StringBuilder comments2 = collectIssueComments(issue2);
		if(comments1.length() != 0 && comments2.length() != 0)
		{
			try {
				commentsSimilarity = CosineTextSimilarity.getCosineSimilarity(comments1.toString()
						                                                    , comments2.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return commentsSimilarity;
	}
	
	public StringBuilder collectIssueComments(JiraIssue issue)
	{
		StringBuilder sb = new StringBuilder();
		Iterator<IssueComment> iterator = issue.getIssueComments().iterator();
		while(iterator.hasNext())
	    {
	        IssueComment setElement = iterator.next();
	        sb.append(setElement.getContent());
	    }
		return sb;
	}
}
