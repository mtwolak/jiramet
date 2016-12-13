package jira;

import database.entity.JiraIssue;

public interface IssuesSimilarity
{
	AssigneeIssueSimilarity getAssigneesWithIssueSimilarities(AssigneeIssues assigneeIssues, JiraIssue jiraIssueToCompare);
}
