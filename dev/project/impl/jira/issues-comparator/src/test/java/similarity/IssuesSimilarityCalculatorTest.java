package similarity;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import database.application.DatabaseApplication;
import database.entity.JiraIssue;
import jira.AssigneeIssueSimilarity;
import jira.AssigneeIssues;
import utils.properties.PropertiesReader;
import utils.properties.Property;

@RunWith(MockitoJUnitRunner.class)
public class IssuesSimilarityCalculatorTest
{
	private IssuesSimilarityCalculator isc;
	@Mock
	private PropertiesReader propertiesReaderMock;
	@Mock
	private DatabaseApplication databaseApplicationMock;
	@Mock
	private IssuesSimilarityCommentsCollector issueSimilarityHelperMock;
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
			protected IssuesSimilarityCommentsCollector getIssueSimilarityHelper()
			{
				return issueSimilarityHelperMock;
			}
		};
		isc.init();
	}
	
	@Test
	public void shouldGetIssuesSimilarity()
	{
		// given
		setWeigths(0.45, 0.45, 0.1);
		List<JiraIssue> jiraIssues = createFakeJiraIssues(2);
		
		// when
		Mockito.when(textsSimilarityMock.getSimilarity(Mockito.anyString(), Mockito.anyString())).thenReturn(0.5);
		Mockito.when(issueSimilarityHelperMock.collectIssueComments(Mockito.any(JiraIssue.class))).thenReturn(new StringBuilder());
		
		//then
		assertThat(isc.getIssuesSimilarity(jiraIssues.get(0), jiraIssues.get(1)), is(0.5));
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
	
	private void setWeigths(double summWeight, double descWeigtht, double commWeight)
	{
		if((summWeight + descWeigtht + commWeight) == 1.0)
		{
			Mockito.when(propertiesReaderMock.getAsDouble(Property.SUMMARY_WEIGHT)).thenReturn(summWeight);
			Mockito.when(propertiesReaderMock.getAsDouble(Property.DESCRIPTION_WEIGHT)).thenReturn(descWeigtht);
			Mockito.when(propertiesReaderMock.getAsDouble(Property.COMMENTS_WEIGHT)).thenReturn(commWeight);
		}
		else
			throw new UnsupportedOperationException("Sum of weights must be 1.0!");
	}

}
