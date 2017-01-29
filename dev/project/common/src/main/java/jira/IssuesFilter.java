package jira;

import java.util.List;

import database.entity.JiraProject;

/**
 * Interface for retrieving assigned issues for jira project
 *
 */
public interface IssuesFilter
{
	/**
	 * Gets jira assignees from jira project according to added jira filters
	 * @param jiraProject jira project from which assigneeIssues will be retrieved
	 * @return list of all assignees from project with theirs assigned jira issues
	 */
	List<AssigneeIssues> getAssignedIssues(JiraProject jiraProject);

}
