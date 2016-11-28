package printer;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import database.entity.Assignee;
import database.entity.JiraIssue;
import database.entity.JiraProject;
import jira.AssigneeIssueTime;
//ignored due to broken preferences
@Ignore
public class PredictionTextComposerTest
{

	@Test
	public void shouldPrintPredictionToFile()
	{
		PredictionTextComposer predictionTextComposer = new PredictionTextComposer();
		predictionTextComposer.printPrediction(createJiraIssue(), createAssigneeAndTimes());
	}

	private JiraIssue createJiraIssue()
	{
		JiraIssue jiraIssue = new JiraIssue();
		jiraIssue.setJiraProject(getJiraProject());
		jiraIssue.setDescription("Jira description");
		return jiraIssue;
	}

	private JiraProject getJiraProject()
	{
		JiraProject jiraProject = new JiraProject();
		jiraProject.setProjectName("Project name");
		return jiraProject;
	}

	private List<AssigneeIssueTime> createAssigneeAndTimes()
	{
		List<AssigneeIssueTime> assigneeIssueTimes = new ArrayList<>();
		assigneeIssueTimes.add(new AssigneeIssueTime(createAssignee("Marcin"), 12));
		assigneeIssueTimes.add(new AssigneeIssueTime(createAssignee("Tomasz"), 2.67));
		assigneeIssueTimes.add(new AssigneeIssueTime(createAssignee("Michal"), 3.14));
		return assigneeIssueTimes;
	}

	private Assignee createAssignee(String assigneeName)
	{
		Assignee assignee = new Assignee();
		assignee.setName(assigneeName);
		return assignee;
	}

}
