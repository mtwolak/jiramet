package database.entity.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import database.entity.AssignedIssue;
import database.entity.Assignee;
import database.entity.JiraIssue;
/**
 * Class for retrieving jira issues assigned for specified assignee
 * @author Marcin
 *
 */
public final class AssigneeConverter
{

	private AssigneeConverter()
	{

	}

	/**
	 * Gets jira issues assigned to specified assignee
	 * @param assignee assignee for who issues are searched for
	 * @return assigned jira issues for assignee
	 */
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
