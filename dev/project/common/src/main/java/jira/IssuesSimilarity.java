package jira;

import java.util.List;

import database.entity.JiraIssue;

public interface IssuesSimilarity
{
	List<AssigneeIssueSimilarity> getAssigneesWithIssueSimilarities(List<AssigneeIssues> assigneeIssues, JiraIssue jiraIssueToCompare);
	
}
