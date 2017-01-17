package filter.custom;

import jira.AssigneeIssues;
import utils.properties.PropertiesReader;

/**
 * Interface for discarding jira issues which does not fulfil requirements
 *
 */
public interface JiraIssueFilter
{
	/**
	 * 
	 * @param assigneeWithHisIssues - jira assignee with assigned issues
	 * @param propertiesReader - property reader
	 * @return true if assignee fulfill requirements
	 */
	boolean filter(AssigneeIssues assigneeWithHisIssues, PropertiesReader propertiesReader);
}
