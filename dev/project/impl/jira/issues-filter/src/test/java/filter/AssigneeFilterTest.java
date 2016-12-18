package filter;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import database.application.DatabaseApplication;
import database.entity.Assignee;
import database.entity.JiraProject;
import filter.AssigneeFilter;
import jira.AssigneeIssues;
import utils.properties.PropertiesReader;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class AssigneeFilterTest
{

	private AssigneeFilter assigneeFilter;
	@Mock
	private PropertiesReader propertiesReaderMock;
	@Mock
	private DatabaseApplication databaseMock;
	@Mock
	private JiraFilterChecker JiraFilterCheckerMock;
	@Mock
	private Assignee assignee;
	@Mock
	private JiraProject jiraProject;

	@Test
	public void shouldReturnFullListWhenNoFiltersAreOk()
	{
		// given
		int numberOfAssignessInDatabase = 5;
		createAssigneesInDatabase(numberOfAssignessInDatabase);
		setFiltersOk();

		// when
		assigneeFilter = createTestedObject();

		// then
		List<AssigneeIssues> filteresAssigneeList = assigneeFilter.getAssignedIssues(jiraProject);
		assertThat(filteresAssigneeList.size(), is(numberOfAssignessInDatabase));
	}

	@Test
	public void shouldReturnEmptyListWhenFiltersAreNotOk()
	{
		// given
		createAssigneesInDatabase(10);
		setFilterFailedForEveryAssignee();

		// when
		assigneeFilter = createTestedObject();
		List<AssigneeIssues> filteresAssigneeList = assigneeFilter.getAssignedIssues(jiraProject);

		// then
		assertTrue(filteresAssigneeList.isEmpty());

	}
	
	private void setFiltersOk()
	{
		when(JiraFilterCheckerMock.check(Mockito.any(AssigneeIssues.class))).thenReturn(true);
	}

	private void setFilterFailedForEveryAssignee()
	{
		when(JiraFilterCheckerMock.check(Mockito.any(AssigneeIssues.class))).thenReturn(false);
	}

	private void createAssigneesInDatabase(int numberFakeAssignees)
	{
		when(databaseMock.getJiraAssignees(jiraProject)).thenReturn(createFakeJiraAssigneess(numberFakeAssignees));
	}

	private List<Assignee> createFakeJiraAssigneess(int numberFakeAssignees)
	{
		List<Assignee> assignees = new ArrayList<>();
		for (int i = 0; i < numberFakeAssignees; i++)
		{
			assignees.add(assignee);
		}
		return assignees;
	}

	private AssigneeFilter createTestedObject()
	{
		AssigneeFilter assigneeFilter = new AssigneeFilter(propertiesReaderMock, databaseMock)
		{

			@Override
			protected JiraFilterChecker createJiraIssueChecker()
			{
				return JiraFilterCheckerMock;
			}
		};
		assigneeFilter.init();
		return assigneeFilter;
	}

}
