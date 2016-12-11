package filter;

import jira.AssigneeIssues;
import utils.properties.PropertiesReader;

public interface JiraIssueFilter
{
	boolean filter(AssigneeIssues assigneeWithHisIssues, PropertiesReader propertiesReader);
}
