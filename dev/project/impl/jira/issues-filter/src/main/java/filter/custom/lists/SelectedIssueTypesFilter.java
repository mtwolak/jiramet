package filter.custom.lists;

import database.entity.JiraIssue;
import utils.properties.PropertiesReader;
import utils.properties.Property;

public class SelectedIssueTypesFilter extends AbstractListJiraIssueFilter
{

	@Override
	protected String getParameterFromJiraIssueToCheckWithFilter(JiraIssue jiraIssue)
	{
		return jiraIssue.getIssueType().getTypeName();
	}

	@Override
	protected Property getParameterForRetrieveStringAllowedListToFilter(PropertiesReader propertiesReader)
	{
		return Property.FILTER_ISSUE_TYPE_LIST;
	}

}
