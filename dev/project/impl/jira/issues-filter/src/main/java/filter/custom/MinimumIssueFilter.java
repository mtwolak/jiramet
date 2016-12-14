package filter.custom;

import jira.AssigneeIssues;
import utils.properties.PropertiesReader;
import utils.properties.Property;

public class MinimumIssueFilter implements JiraIssueFilter
{

	@Override
	public boolean filter(AssigneeIssues assigneeWithIssues, PropertiesReader propertiesReader)
	{
		return assigneeWithIssues.getAssignedJiraIssues().size() >= propertiesReader.getAsInt(Property.FILTER_MIN_NUMBER_OF_ISSUES_FOR_DEVELOPER);
	}

}
