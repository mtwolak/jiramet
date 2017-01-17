package filter;

import java.util.ArrayList;
import java.util.List;

import filter.custom.JiraIssueFilter;
import jira.AssigneeIssues;
import utils.properties.PropertiesReader;

/**
 * Class for filtering issues according to added filters for assignee
 */
public class JiraFilterChecker
{

	private PropertiesReader propertiesReader;
	private List<JiraIssueFilter> filters;

	/**
	 * 
	 * @param propertiesReader
	 *            properties
	 */
	public JiraFilterChecker(PropertiesReader propertiesReader)
	{
		this.propertiesReader = propertiesReader;
		this.filters = new ArrayList<>();
	}

	/**
	 * Appending new jira filter
	 * 
	 * @param filter
	 *            filter, which will be using in checking method for validating
	 *            filters
	 */
	public void add(JiraIssueFilter filter)
	{
		this.filters.add(filter);
	}

	/**
	 * Method for validating, whether all issues assigned to person fulfill all
	 * requirements
	 * 
	 * @param assigneeWithHisIssues
	 *            assignee with his jira issues
	 * @return true, if all issues passed all added filters
	 */
	public boolean check(AssigneeIssues assigneeWithHisIssues)
	{
		for (JiraIssueFilter filter : filters)
		{
			if (!filter.filter(assigneeWithHisIssues, propertiesReader))
			{
				return false;
			}
		}
		return true;
	}

}
