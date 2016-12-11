package database.entity.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import database.entity.AssignedIssue;
import database.entity.Assignee;
import database.entity.JiraIssue;

public final class AssigneeConverter
{

	private AssigneeConverter()
	{

	}

	public static List<JiraIssue> getAssignedJiraIssues(Assignee assignee)
	{
		Set<AssignedIssue> originalAssignedIssues = assignee.getAssignedIssue();
		List<JiraIssue> jiraIssues = new ArrayList<JiraIssue>(originalAssignedIssues.size());
		for (AssignedIssue assignedIssue : originalAssignedIssues)
		{
			jiraIssues.add(assignedIssue.getJiraIssue());
		}
		return jiraIssues;
	}

}
