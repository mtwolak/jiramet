package database.entity.converter;

import java.util.ArrayList;
import java.util.List;

import database.entity.AssignedIssue;
import database.entity.Assignee;
import database.entity.JiraIssue;

public final class AssigneeConverter
{

	public static List<JiraIssue> getAssignedJiraIssues(Assignee assignee)
	{
		List<JiraIssue> jiraIssues = new ArrayList<JiraIssue>();
		for (AssignedIssue assignedIssue : assignee.getAssignedIssue())
		{
			jiraIssues.add(assignedIssue.getJiraIssue());
		}
		return jiraIssues;
	}

}
