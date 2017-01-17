package filter.custom;

import java.util.ArrayList;
import java.util.List;

import database.entity.JiraIssue;
import jira.AssigneeIssues;
import utils.properties.PropertiesReader;
import utils.properties.Property;

/**
 * Class for filtering issues with minimum size description
 */
public class MinimumIssueDescripionSizeFilter implements JiraIssueFilter
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean filter(AssigneeIssues assigneeWithHisIssues, PropertiesReader propertiesReader)
	{
		List<JiraIssue> assignedJiraIssues = new ArrayList<>();
		for (JiraIssue jiraIssue : assigneeWithHisIssues.getAssignedJiraIssues())
		{
			addJiraIssueWithCorrectDescriptionLength(propertiesReader, assignedJiraIssues, jiraIssue);
		}
		assigneeWithHisIssues.setAssignedJiraIssues(assignedJiraIssues);
		return true;
	}

	private void addJiraIssueWithCorrectDescriptionLength(PropertiesReader propertiesReader, List<JiraIssue> assignedJiraIssues,
			JiraIssue jiraIssue)
	{
		String issueDescription = jiraIssue.getDescription();
		if (issueDescription != null && isJiraDescriptionLengthCorrect(propertiesReader, issueDescription))
		{
			assignedJiraIssues.add(jiraIssue);
		}
	}

	private boolean isJiraDescriptionLengthCorrect(PropertiesReader propertiesReader, String issueDescription)
	{
		return issueDescription.length() >= propertiesReader.getAsInt(Property.FILTER_MIN_DESCRIPTION_LONG_SIZE);
	}

}
