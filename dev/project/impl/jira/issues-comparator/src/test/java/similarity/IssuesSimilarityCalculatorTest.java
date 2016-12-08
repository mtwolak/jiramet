package similarity;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import database.application.DatabaseApplication;
import database.entity.JiraIssue;
import jira.AssigneeIssueSimilarity;
import jira.AssigneeIssues;
import utils.properties.PropertiesReader;

@RunWith(MockitoJUnitRunner.class)
public class IssuesSimilarityCalculatorTest
{
	private IssuesSimilarityCalculator isc;
	@Mock
	private PropertiesReader propertiesReaderMock;
	@Mock
	private DatabaseApplication databaseApplicationMock;
	@Mock
	private IssuesSimilarityHelper issueSimilarityHelperMock;
	@Mock
	private TextSimilarity textsSimilarityMock;
	@Mock
	private JiraIssue jiraIssueMock;
	@Mock
	private AssigneeIssues assigneeIssueMock;

	@Before
	public void setUp()
	{
		isc = new IssuesSimilarityCalculator(propertiesReaderMock, databaseApplicationMock, textsSimilarityMock)
		{
			@Override
			protected IssuesSimilarityHelper getIssueSimilarityHelper()
			{
				return issueSimilarityHelperMock;
			}
		};
		isc.init();
	}

	@Test
	public void shouldGetAssigneeWithIssueSimilarity()
	{
		// given
		int numberOfAssigneeIssues = 5;
		List<AssigneeIssues> assigneeIssuesList = createFakeAssigneeIssues(numberOfAssigneeIssues);
		JiraIssue jiraIssue = createFakeJiraIssues(1).get(0);

		// when
		List<AssigneeIssueSimilarity> assigneeIssuesSimilarityList = isc.getAssigneesWithIssueSimilarities(assigneeIssuesList, jiraIssue);
		
		// then
		assertThat(assigneeIssuesSimilarityList.size(), is(numberOfAssigneeIssues));
	}
	
	private List<JiraIssue> createFakeJiraIssues(int numberOfFakeJiraIssues)
	{
		List<JiraIssue> jiraIssues = new ArrayList<>();
		for (int i = 0; i < numberOfFakeJiraIssues; i++)
		{
			jiraIssues.add(jiraIssueMock);
		}
		return jiraIssues;
	}
	
	private List<AssigneeIssues> createFakeAssigneeIssues(int numberOfFakeAssigneeIssues)
	{
		List<AssigneeIssues> assigneeIssues = new ArrayList<>();
		for (int i = 0; i < numberOfFakeAssigneeIssues; i++)
		{
			assigneeIssues.add(assigneeIssueMock);
		}
		return assigneeIssues;
	}
	
	private double checkSimilarityCorrectness(double similarity)
	{
		if (similarity < 0 || similarity > 1)
		{
			return -1;
		}
		return similarity;
	}


}
