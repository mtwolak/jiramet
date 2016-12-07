package jira;

import java.util.List;

public interface IssuesSimilarity
{
	List<AssigneeIssueSimilarity> getIssuesSimilarityList(List<AssigneeIssues> assigneeIssues);
	
}
