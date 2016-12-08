package filter;

import java.util.ArrayList;
import java.util.List;

import database.entity.Assignee;
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

	public boolean check(Assignee assignee)
	{
		for (JiraIssueFilter filter : filters)
		{
			if (!filter.filter(assignee, propertiesReader))
			{
				return false;
			}
		}
		return true;
	}

}
