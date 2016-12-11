package filter;

import java.util.ArrayList;
import java.util.List;

import database.application.DatabaseApplication;
import database.entity.Assignee;
import jira.AssigneeIssues;
import jira.IssuesFilter;
import utils.properties.PropertiesReader;

public class AssigneeFilter implements IssuesFilter
{
	private PropertiesReader propertiesReader;
	private DatabaseApplication dba;
	private JiraFilterChecker jiraFilterChecker;

	public AssigneeFilter(PropertiesReader propertiesReader, DatabaseApplication databaseApplication)
	{
		this.propertiesReader = propertiesReader;
		this.dba = databaseApplication;
	}

	public void init()
	{
		jiraFilterChecker = createJiraIssueChecker();
	}

	protected JiraFilterChecker createJiraIssueChecker()
	{
		return new JiraFilterChecker(propertiesReader);
	}

	public AssigneeFilter addFilter(JiraIssueFilter filter)
	{
		this.jiraFilterChecker.add(filter);
		return this;
	}

	@SuppressWarnings("unchecked")
	public List<AssigneeIssues> getAssignedIssues()
	{
		List<AssigneeIssues> assigneeIssues = new ArrayList<AssigneeIssues>();
		for (Assignee assignee : (List<Assignee>) dba.getJiraAssignees())
		{
			addAssigneeIssueAccordingToFilter(assignee, assigneeIssues);
		}
		return assigneeIssues;
	}

	private void addAssigneeIssueAccordingToFilter(Assignee assignee, List<AssigneeIssues> assigneeIssues)
	{
		AssigneeIssues assigneeWithIssues = new AssigneeIssues(assignee);
		if (areFiltersOk(assigneeWithIssues))
		{
			assigneeIssues.add(assigneeWithIssues);
		}
	}

	private boolean areFiltersOk(AssigneeIssues assigneeWithIssues)
	{
		return jiraFilterChecker.check(assigneeWithIssues);
	}

}
