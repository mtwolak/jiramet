package jira;

import java.util.List;

import database.entity.JiraProject;

public interface IssuesFilter
{
	List<AssigneeIssues> getAssignedIssues(JiraProject jiraProject);
	
}
