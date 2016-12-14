package filter;

import java.util.ArrayList;
import java.util.List;

import filter.custom.JiraIssueFilter;
import jira.AssigneeIssues;
import utils.properties.PropertiesReader;

public class JiraFilterChecker
{

	private PropertiesReader propertiesReader;
	private List<JiraIssueFilter> filters;

	public JiraFilterChecker(PropertiesReader propertiesReader)
	{
		this.propertiesReader = propertiesReader;
		this.filters = new ArrayList<>();
	}

	public void add(JiraIssueFilter filter)
	{
		this.filters.add(filter);
	}

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
