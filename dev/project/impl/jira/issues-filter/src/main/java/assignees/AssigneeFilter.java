package assignees;

import java.util.ArrayList;
import java.util.List;

import database.application.DatabaseApplication;
import database.entity.Assignee;
import database.entity.JiraIssue;
import jira.AssigneeIssues;
import utils.properties.PropertiesReader;

public class AssigneeFilter 
{
	private DatabaseApplication dba;
	public static int MIN_NUMBER_OF_ISSUES_FOR_DEVELOPER = 5;
	
	public AssigneeFilter(PropertiesReader propertiesReader)
	{
		this.dba = new DatabaseApplication(propertiesReader);
	}
	
	@SuppressWarnings("unchecked")
	public List<AssigneeIssues> getFilteredAssigneeIssuesList()
	{
		List<AssigneeIssues> assigneeIssues = new ArrayList<AssigneeIssues>();
		List<JiraIssue> assignedJiraIssues;
		for(Assignee assignee : (List<Assignee>) dba.getJiraAssignees())
		{
			assignedJiraIssues = assignee.getAssigneedJiraIssues();
			if(assignedJiraIssues.size() >= MIN_NUMBER_OF_ISSUES_FOR_DEVELOPER)
				assigneeIssues.add(new AssigneeIssues(assignee, assignee.getAssigneedJiraIssues()));
		}
		return assigneeIssues;
	}

}
