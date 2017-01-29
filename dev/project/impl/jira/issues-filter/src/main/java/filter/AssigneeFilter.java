package filter;

import java.util.ArrayList;
import java.util.List;

import database.application.DatabaseApplication;
import database.entity.Assignee;
import database.entity.JiraProject;
import filter.custom.JiraIssueFilter;
import jira.AssigneeIssues;
import jira.IssuesFilter;
import utils.properties.PropertiesReader;

/**
 * 
 * Class for retrieving assignee according to added filters
 */
public class AssigneeFilter implements IssuesFilter
{
	private PropertiesReader propertiesReader;
	private DatabaseApplication dba;
	private JiraFilterChecker jiraFilterChecker;

	/**
	 * @param propertiesReader
	 *            property reader
	 * @param databaseApplication
	 *            database configuration
	 */
	public AssigneeFilter(PropertiesReader propertiesReader, DatabaseApplication databaseApplication)
	{
		this.propertiesReader = propertiesReader;
		this.dba = databaseApplication;
	}

	/**
	 * Initialization of class
	 */
	public void init()
	{
		jiraFilterChecker = createJiraIssueChecker();
	}

	protected JiraFilterChecker createJiraIssueChecker()
	{
		return new JiraFilterChecker(propertiesReader);
	}

	/**
	 * 
	 * @param filter
	 *            jiraFilter to be added
	 * @return AssigneeFilter class with added filter
	 */
	public AssigneeFilter addFilter(JiraIssueFilter filter)
	{
		this.jiraFilterChecker.add(filter);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<AssigneeIssues> getAssignedIssues(JiraProject jiraProject)
	{
		List<Assignee> jiraAssignees = dba.getJiraAssignees(jiraProject);
		List<AssigneeIssues> assigneeIssues = new ArrayList<AssigneeIssues>(jiraAssignees.size());
		for (Assignee assignee : jiraAssignees)
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
