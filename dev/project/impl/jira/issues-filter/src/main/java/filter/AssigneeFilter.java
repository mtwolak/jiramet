package filter;

import java.util.ArrayList;
import java.util.List;

import database.application.DatabaseApplication;
import database.entity.Assignee;
import jira.AssigneeIssues;
import utils.properties.PropertiesReader;

public class AssigneeFilter
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
	public List<AssigneeIssues> getFilteredAssigneeIssuesList()
	{
		List<AssigneeIssues> assigneeIssues = new ArrayList<AssigneeIssues>();
		for (Assignee assignee : (List<Assignee>) dba.getJiraAssignees())
		{
			addAssigneeIssueAccordingToFilter(assigneeIssues, assignee);
		}
		return assigneeIssues;
	}

	private void addAssigneeIssueAccordingToFilter(List<AssigneeIssues> assigneeIssues, Assignee assignee)
	{
		if (areFiltersOk(assignee))
		{
			assigneeIssues.add(new AssigneeIssues(assignee, assignee.getAssignedJiraIssues()));
		}
	}

	private boolean areFiltersOk(Assignee assignee)
	{
		return jiraFilterChecker.check(assignee);
	}

}
