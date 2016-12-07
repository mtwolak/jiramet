package assignees;

import java.util.ArrayList;
import java.util.List;

import database.application.DatabaseApplication;
import database.entity.Assignee;
import database.entity.JiraIssue;
import jira.AssigneeIssues;
import utils.properties.PropertiesReader;
import utils.properties.Property;

public class AssigneeFilter 
{
	private PropertiesReader propertiesReader;
	private DatabaseApplication dba;
	
	public AssigneeFilter(PropertiesReader propertiesReader)
	{
		this.propertiesReader = propertiesReader;
		this.dba = new DatabaseApplication(propertiesReader);
	}
	
	@SuppressWarnings("unchecked")
	public List<AssigneeIssues> getFilteredAssigneeIssuesList()
	{
		List<AssigneeIssues> assigneeIssues = new ArrayList<AssigneeIssues>();
		List<JiraIssue> assignedJiraIssues;
		for(Assignee assignee : (List<Assignee>) dba.getJiraAssignees())
		{
			assignedJiraIssues = assignee.getAssignedJiraIssues();
			if(assignedJiraIssues.size() >= propertiesReader.getAsInt(Property.MIN_NUMBER_OF_ISSUES_FOR_DEVELOPER))
				assigneeIssues.add(new AssigneeIssues(assignee, assignedJiraIssues));
		}
		return assigneeIssues;
	}

}
