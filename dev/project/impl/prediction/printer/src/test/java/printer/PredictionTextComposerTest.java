package printer;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import database.entity.Assignee;
import database.entity.JiraIssue;
import database.entity.JiraProject;
import jira.AssigneeTimeResolve;

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

	private List<AssigneeTimeResolve> createAssigneeAndTimes()
	{
		List<AssigneeTimeResolve> assigneeIssueTimes = new ArrayList<>();
		assigneeIssueTimes.add(new AssigneeTimeResolve(createAssignee("Marcin"), 12));
		assigneeIssueTimes.add(new AssigneeTimeResolve(createAssignee("Tomasz"), 2.67));
		assigneeIssueTimes.add(new AssigneeTimeResolve(createAssignee("Michal"), 3.14));
		return assigneeIssueTimes;
	}

	private Assignee createAssignee(String assigneeName)
	{
		Assignee assignee = new Assignee();
		assignee.setName(assigneeName);
		return assignee;
	}

}
