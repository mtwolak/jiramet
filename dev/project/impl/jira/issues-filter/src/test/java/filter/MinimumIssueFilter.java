package filter;

import database.entity.Assignee;
import utils.properties.PropertiesReader;
import utils.properties.Property;

public class MinimumIssueFilter implements JiraIssueFilter
{

	@Override
	public boolean filter(Assignee assignee, PropertiesReader propertiesReader)
	{
		return assignee.getAssignedJiraIssues().size() >= propertiesReader.getAsInt(Property.MIN_NUMBER_OF_ISSUES_FOR_DEVELOPER);
	}

}
