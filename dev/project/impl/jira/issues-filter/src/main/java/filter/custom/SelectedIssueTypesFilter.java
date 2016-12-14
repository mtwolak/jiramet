package filter.custom;

import java.util.ArrayList;
import java.util.List;

import database.entity.JiraIssue;
import jira.AssigneeIssues;
import utils.properties.PropertiesReader;
import utils.properties.Property;

public class SelectedIssueTypesFilter implements JiraIssueFilter
{

	@Override
	public boolean filter(AssigneeIssues assigneeWithHisIssues, PropertiesReader propertiesReader)
	{
		List<String> allowedJiraTypes = propertiesReader.getAsStringList(Property.FILTER_ISSUE_TYPE_LIST);
		if (!allowedJiraTypes.isEmpty())
		{
			filterWithNotEmptyJiraTypesList(assigneeWithHisIssues, allowedJiraTypes);
		}
		return true;
	}

	private void filterWithNotEmptyJiraTypesList(AssigneeIssues assigneeWithHisIssues, List<String> allowedJiraTypes)
	{
		List<JiraIssue> assignedJiraIssues = assigneeWithHisIssues.getAssignedJiraIssues();
		List<JiraIssue> jiraIssueOutput = new ArrayList<>(assignedJiraIssues.size());
		for (JiraIssue jiraIssue : assignedJiraIssues)
		{
			String issueTypeName = jiraIssue.getIssueType().getTypeName();
			if (allowedJiraTypes.stream().filter(el -> el.equalsIgnoreCase(issueTypeName)).findFirst().isPresent())
			{
				jiraIssueOutput.add(jiraIssue);
			}
		}
		assigneeWithHisIssues.setAssignedJiraIssues(jiraIssueOutput);
	}

}
