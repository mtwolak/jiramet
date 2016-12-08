package jira;

import java.util.List;

import database.entity.JiraIssue;

public interface IssuesFilter
{
	List<AssigneeIssues> getAssignedIssues();
	
}
