package filter;

import database.entity.Assignee;
import utils.properties.PropertiesReader;

public interface JiraIssueFilter
{
	boolean filter(Assignee assignee, PropertiesReader propertiesReader);
}
