package filter;

import java.util.ArrayList;
import java.util.List;

import database.entity.JiraIssue;
import jira.AssigneeIssues;
import utils.properties.PropertiesReader;
import utils.properties.Property;

public class MinimumIssueDescripionSize implements JiraIssueFilter
{

	@Override
	public boolean filter(AssigneeIssues assigneeWithHisIssues, PropertiesReader propertiesReader)
	{
		List<JiraIssue> assignedJiraIssues = new ArrayList<>();
		for(JiraIssue jiraIssue : assigneeWithHisIssues.getAssignedJiraIssues()) {
			if(jiraIssue.getDescription().length() >= propertiesReader.getAsInt(Property.FILTER_MIN_DESCRIPTION_LONG_SIZE)) {
				assignedJiraIssues.add(jiraIssue);
			}
		}
		assigneeWithHisIssues.setAssignedJiraIssues(assignedJiraIssues);
		return true;
	}

}
