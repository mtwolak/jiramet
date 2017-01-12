package filter.custom;

import java.util.ArrayList;
import java.util.List;

import database.application.DatabaseApplication;
import database.entity.JiraIssue;
import jira.AssigneeIssues;
import utils.properties.PropertiesReader;
import utils.properties.Property;

public class AnalyzedIssueFilter implements JiraIssueFilter
{

	@Override
	public boolean filter(AssigneeIssues assigneeWithHisIssues, PropertiesReader propertiesReader) {
		JiraIssue analyzedIssue = getAnalyzedIssueFromDb(propertiesReader);
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
	
	private JiraIssue getAnalyzedIssueFromDb(PropertiesReader propertiesReader)
	{
		DatabaseApplication dba = new DatabaseApplication(propertiesReader);
		return dba.getJiraIssue(propertiesReader.getAsInt(Property.PROJECT_ID_JIRA_ISSUE_TO_ANALYZE));
	}

}
