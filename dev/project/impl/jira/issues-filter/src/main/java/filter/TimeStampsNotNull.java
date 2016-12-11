package filter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import database.entity.AssignedIssue;
import database.entity.JiraIssue;
import jira.AssigneeIssues;
import utils.properties.PropertiesReader;

public class TimeStampsNotNull implements JiraIssueFilter
{

	@Override
	public boolean filter(AssigneeIssues assigneeWithHisIssues, PropertiesReader propertiesReader)
	{
		List<JiraIssue> assignedJiraIssues = new ArrayList<>(assigneeWithHisIssues.getAssignedJiraIssues().size());
		for (JiraIssue assignedIssue : assigneeWithHisIssues.getAssignedJiraIssues())
		{
			AssignedIssue currentAssignedIssue = assignedIssue.getAssignedIssues().iterator().next();
			Timestamp createdAt = currentAssignedIssue.getJiraIssue().getCreatedAt();
			Timestamp finish = currentAssignedIssue.getResolvedAt();
			if (createdAt != null && finish != null)
			{
				assignedJiraIssues.add(assignedIssue);
			}
		}
		assigneeWithHisIssues.setAssignedJiraIssues(assignedJiraIssues);
		return true;
	}

}
