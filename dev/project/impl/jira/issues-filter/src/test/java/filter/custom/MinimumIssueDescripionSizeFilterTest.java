package filter.custom;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import database.entity.AssignedIssue;
import database.entity.Assignee;
import database.entity.JiraIssue;
import filter.custom.MinimumIssueDescripionSizeFilter;
import jira.AssigneeIssues;
import utils.properties.PropertiesReader;
import utils.properties.Property;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

import static org.hamcrest.Matchers.*;

@RunWith(MockitoJUnitRunner.class)
public class MinimumIssueDescripionSizeFilterTest
{
	@Mock
	private PropertiesReader propertiesReaderMock;
	@Mock
	private Assignee assigneeMock;

	private static final int MIN_ISSUE_DESCRIPTION_LENGTH = 10;

	@Test
	public void shouldRemoveIssuesWithNotLongEnoughDescription()
	{
		setMocks();
		AssigneeIssues assigneeWithIssues = createAssigneeWithIssues();
		JiraIssueFilter filter = new MinimumIssueDescripionSizeFilter();

		filter.filter(assigneeWithIssues, propertiesReaderMock);

		assertThat(assigneeWithIssues.getAssignedJiraIssues().size(), is(1));

	}

	private void setMocks()
	{
		when(assigneeMock.getAssignedIssue()).thenReturn(createAssignedIssues());
		when(propertiesReaderMock.getAsInt(Property.FILTER_MIN_DESCRIPTION_LONG_SIZE)).thenReturn(MIN_ISSUE_DESCRIPTION_LENGTH);
	}

	private AssigneeIssues createAssigneeWithIssues()
	{
		AssigneeIssues assigneeIssues = new AssigneeIssues(assigneeMock);
		return assigneeIssues;
	}

	private Set<AssignedIssue> createAssignedIssues()
	{
		Set<AssignedIssue> issues = new HashSet<>();
		issues.add(createValidIssueWithLongEnouthDescription());
		issues.add(createInvalidIssueWithTooShortDescription());
		return issues;
	}

	private AssignedIssue createInvalidIssueWithTooShortDescription()
	{
		return createIssue(" : )");
	}

	private AssignedIssue createIssue(String issueDescription)
	{
		AssignedIssue assignedIssue = new AssignedIssue();
		assignedIssue.setJiraIssue(createJiraIssue(issueDescription));
		return assignedIssue;
	}

	private AssignedIssue createValidIssueWithLongEnouthDescription()
	{
		return createIssue("Long enough issue description");
	}

	private JiraIssue createJiraIssue(String issueDescription)
	{
		JiraIssue jiraIssue = new JiraIssue();
		jiraIssue.setDescription(issueDescription);
		return jiraIssue;
	}

}
