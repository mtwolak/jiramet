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
		List<JiraIssue> issues = dba.getJiraIssues(project);
		for(JiraIssue jissue : issues)
        {
			if(jissue.getJiraIssueId() != issue.getJiraIssueId())
				similarities.add(new Pair<Integer, Double>(jissue.getJiraIssueId(), getIssueSimilarity(issue, jissue)));
        }
		dba.closeSession();
		
		return similarities;
	}	
	
	public double getIssueSimilarity(JiraIssue issue1, JiraIssue issue2)
	{
		try 
		{
			/*System.out.println("===========================================");
			PersistentSet comments1 = (PersistentSet) issue1.getIssueComments();
			Iterator<IssueComment> iterator = comments1.iterator();
		    while(iterator.hasNext()) {
		        IssueComment setElement = iterator.next();
		        if(setElement==null) {
		            iterator.remove();
		        }
		        else
		        {
		        	System.out.println(setElement.getContent());
		        }
		    }*/
			
			String description1 = issue1.getDescription();
			String description2 = issue2.getDescription();
			double descriptionSimilarity = 0;
			if(description1 != null && description2 != null)
			{
			   CosineTextSimilarity cts1 = new CosineTextSimilarity(description1, description2);
			   descriptionSimilarity = cts1.getCosineSimilarity(description1, description2);
			   return descriptionSimilarity;
		    }
		    
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return -1.0;
	}
}
