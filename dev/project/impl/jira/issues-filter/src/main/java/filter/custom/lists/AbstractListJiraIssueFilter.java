package filter.custom.lists;

import java.util.ArrayList;
import java.util.List;

import database.entity.JiraIssue;
import filter.custom.JiraIssueFilter;
import jira.AssigneeIssues;
import utils.properties.PropertiesReader;
import utils.properties.Property;
/**
 * 
 * Class used for creating filters which need list as a filter
 *
 */
public abstract class AbstractListJiraIssueFilter implements JiraIssueFilter
{
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean filter(AssigneeIssues assigneeWithHisIssues, PropertiesReader propertiesReader)
	{
		List<String> listWithAllowedTypesForFiltering = getListWithAllowedTypesForFiltering(propertiesReader,
				getParameterForRetrieveStringAllowedListToFilter(propertiesReader));
		if (!listWithAllowedTypesForFiltering.isEmpty())
		{
			filterWithNotEmptyFilterTypes(assigneeWithHisIssues, listWithAllowedTypesForFiltering);
		}
		return true;
	}

	private void filterWithNotEmptyFilterTypes(AssigneeIssues assigneeWithHisIssues, List<String> listWithAllowedTypesForFiltering)
	{
		List<JiraIssue> assignedJiraIssues = assigneeWithHisIssues.getAssignedJiraIssues();
		List<JiraIssue> jiraIssueOutput = new ArrayList<>(assignedJiraIssues.size());
		for (JiraIssue jiraIssue : assignedJiraIssues)
		{
			String issueTypeName = getParameterFromJiraIssueToCheckWithFilter(jiraIssue);
			if (listWithAllowedTypesForFiltering.stream().filter(el -> el.equalsIgnoreCase(issueTypeName)).findFirst().isPresent())
			{
				jiraIssueOutput.add(jiraIssue);
			}
		}
		assigneeWithHisIssues.setAssignedJiraIssues(jiraIssueOutput);

	}

	private List<String> getListWithAllowedTypesForFiltering(PropertiesReader propertiesReader, Property listWithAllowedTypesForFiltering)
	{
		return propertiesReader.getAsStringList(listWithAllowedTypesForFiltering);
	}

	protected abstract Property getParameterForRetrieveStringAllowedListToFilter(PropertiesReader propertiesReader);

	protected abstract String getParameterFromJiraIssueToCheckWithFilter(JiraIssue jiraIssue);

}
