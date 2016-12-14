package filter.custom.lists;

import database.entity.JiraIssue;
import utils.properties.PropertiesReader;
import utils.properties.Property;

public class SelectedIssuePriorityFilter extends AbstractListJiraIssueFilter
{

	@Override
	protected String getParameterFromJiraIssueToCheckWithFilter(JiraIssue jiraIssue)
	{
		return jiraIssue.getIssuePriority().getPriorityName();
	}

	@Override
	protected Property getParameterForRetrieveStringAllowedListToFilter(PropertiesReader propertiesReader)
	{
		return Property.FILTER_ISSUE_PRIORITY_LIST;
	}


}
