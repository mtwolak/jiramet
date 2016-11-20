package similarity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.collection.internal.PersistentSet;
import org.hibernate.mapping.Set;

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
		dba = new DatabaseApplication();
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
		double issuesSimilarity = 1.0;
		try 
		{
			/*double titlesSimilarity = getIssuesTitlesSimilarity(issue1, issue2);*/
			/*double descriptionsSimilarity = getIssuesDescriptionsSimilarity(issue1, issue2);*/
			/*double commentsSimilarity = getIssuesCommentsSimilarity(issue1, issue2);*/

		    
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return issuesSimilarity;
	}
	
	public double getIssuesTitlesSimilarity(JiraIssue issue1, JiraIssue issue2)
	{
		try 
		{
			
		    
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return -1.0;
	}
	
	public double getIssuesDescriptionsSimilarity(JiraIssue issue1, JiraIssue issue2)
	{
		try 
		{
			String description1 = issue1.getDescription();
			String description2 = issue2.getDescription();
			double descriptionSimilarity = 0;
			if(description1 != null && description2 != null)
			{
			   descriptionSimilarity = CosineTextSimilarity.getCosineSimilarity(description1, description2);
			   return descriptionSimilarity;
		    }
		    
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return -1.0;
	}
	
	public double getIssuesCommentsSimilarity(JiraIssue issue1, JiraIssue issue2)
	{
		try 
		{
			PersistentSet comments1 = (PersistentSet) issue1.getIssueComments();
			Iterator<IssueComment> iterator = comments1.iterator();
		    while(iterator.hasNext()) {
		        IssueComment setElement = iterator.next();
		        /*if(setElement==null) {
		            iterator.remove();
		        }
		        else
		        {
		        	System.out.println(setElement.getContent());
		        }*/
		        if(setElement!=null) {
		        	System.out.println(setElement.getContent());
		        }
		    }
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return -1.0;
	}
}
