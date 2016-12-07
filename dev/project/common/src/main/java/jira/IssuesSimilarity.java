package jira;

import java.util.List;

import database.entity.JiraIssue;

public interface IssuesSimilarity
{
	List<AssigneeIssueSimilarity> getIssuesSimilarityList(JiraIssue jiraIssue, List<AssigneeIssues> assigneeIssues);
	
}
