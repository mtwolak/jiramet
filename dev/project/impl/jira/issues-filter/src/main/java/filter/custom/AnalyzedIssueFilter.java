package filter.custom;

import java.util.ArrayList;
import java.util.List;

import database.entity.JiraIssue;
import jira.AssigneeIssues;
import utils.properties.PropertiesReader;
/**
 * Class for discarding issue which is being analyzed
 */
public class AnalyzedIssueFilter implements JiraIssueFilter
{

	JiraIssue analyzedIssue;
	
	public AnalyzedIssueFilter(JiraIssue analyzedIssue)
	{
		this.analyzedIssue = analyzedIssue;	
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean filter(AssigneeIssues assigneeWithHisIssues, PropertiesReader propertiesReader) {
		List<JiraIssue> assignedJiraIssues = new ArrayList<>();
		for (JiraIssue jiraIssue : assigneeWithHisIssues.getAssignedJiraIssues())
		{
			addJiraIssueThatIsNotTheSameAsAnalyzedOne(propertiesReader, assignedJiraIssues, jiraIssue, analyzedIssue);
		}
		assigneeWithHisIssues.setAssignedJiraIssues(assignedJiraIssues);
		return true;
	}
	
	private void addJiraIssueThatIsNotTheSameAsAnalyzedOne(PropertiesReader propertiesReader, List<JiraIssue> assignedJiraIssues,
			JiraIssue jiraIssue, JiraIssue analyzedIssue)
	{
		if (jiraIssue.getJiraIssueId() != analyzedIssue.getJiraIssueId())
		{
			assignedJiraIssues.add(jiraIssue);
		}
	}

}
