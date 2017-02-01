package similarity;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import database.entity.Assignee;
import database.entity.JiraIssue;
import jira.AssigneeIssueSimilarity;
import jira.AssigneeIssues;
import jira.JiraIssueSimilarity;
import utils.properties.PropertiesReader;
import utils.properties.Property;

@RunWith(MockitoJUnitRunner.class)
public class IssuesSimilarityCalculatorTest
{
	private IssuesSimilarityCalculator isc;
	@Mock
	private PropertiesReader propertiesReaderMock;
	@Mock
	private IssuesSimilarityCommentsCollector issuesSimilarityCommentsCollectorMock;
	@Mock
	private TextSimilarity textsSimilarityMock;
	@Mock
	private JiraIssue jiraIssueMock;
	@Mock
	private AssigneeIssues assigneeIssueMock;
	@Mock
	private Assignee assignee;
	
	private ListPartitioner listPartitioner;

	@Before
	public void setUp()
	{
		createTestObj();
		createListPartitioner();
	}



	private void createListPartitioner()
	{
		listPartitioner = new ListPartitioner(0) {
			@Override
			public List<JiraIssueSimilarity> getList(List<JiraIssueSimilarity> jiraIssueSimilarities)
			{
				return jiraIssueSimilarities;
			}
		};
	}

	private void createTestObj()
	{
		isc = new IssuesSimilarityCalculator(propertiesReaderMock, textsSimilarityMock, textsSimilarityMock)
		{
			@Override
			protected IssuesSimilarityCommentsCollector getIssuesSimilarityCommentsCollector()
			{
				return issuesSimilarityCommentsCollectorMock;
			}
		};
		isc.init();
		isc.setListPartitioner(listPartitioner);
	}
	
	
	
	@Test
	public void shouldGetIssuesSimilarity()
	{
		// given
		setWeights(0.45, 0.45, 0.1);
		List<JiraIssue> jiraIssues = createFakeJiraIssues(2);
		
		// when
		Mockito.when(textsSimilarityMock.getSimilarity(Mockito.anyString(), Mockito.anyString())).thenReturn(0.5);
		Mockito.when(issuesSimilarityCommentsCollectorMock.collectIssueComments(Mockito.any(JiraIssue.class))).thenReturn(new StringBuilder());
		
		//then
		assertThat(isc.getIssuesSimilarity(jiraIssues.get(0), jiraIssues.get(1)), is(0.45));
	}
	
	@Test
	public void shouldCheckSimilarityForIssueWithComment()
	{
		setWeights(0.45, 0.45, 0.1);
		List<JiraIssue> jiraIssues = createFakeJiraIssues(2);
		
		// when
		Mockito.when(textsSimilarityMock.getSimilarity(Mockito.anyString(), Mockito.anyString())).thenReturn(0.5);
		Mockito.when(issuesSimilarityCommentsCollectorMock.collectIssueComments(Mockito.any(JiraIssue.class))).thenReturn(new StringBuilder("AnyComment"));
		
		//then
		assertThat(isc.getIssuesSimilarity(jiraIssues.get(0), jiraIssues.get(1)), is(0.5));
	}
	
	@Test
	public void shouldComputeSimilarityForAssigneeForMinAlphaWhichAddsOneSimilarity()
	{
		String desc1 = "desc1";
		String desc2 = "desc2";
		setWeights(0.45, 0.45, 0.1);
		Mockito.when(propertiesReaderMock.getAsDouble(Property.MODEL_MIN_ALPHA)).thenReturn(0.1);
		Mockito.when(assigneeIssueMock.getAssignedJiraIssues()).thenReturn(createAssignedJiraIssues(desc1, desc2));
		Mockito.when(assigneeIssueMock.getAssignee()).thenReturn(assignee);
		createTestObj();
		
		Mockito.when(textsSimilarityMock.getSimilarity("desc", desc1)).thenReturn(0.5);
		AssigneeIssueSimilarity assigneesWithIssueSimilarities = isc.getAssigneesWithIssueSimilarities(assigneeIssueMock, createJiraIssue(3, "Sum", "desc"), null);
		
		assertThat(assigneesWithIssueSimilarities.getAssignee(), Matchers.is(assignee));
		assertThat(assigneesWithIssueSimilarities.getAssignedJiraIssues().size(), Matchers.is(1));
		
	}

	private List<JiraIssue> createAssignedJiraIssues(String desc1, String desc2)
	{
		List<JiraIssue> list = new ArrayList<>();
		list.add(createJiraIssue(1, "Summary", desc1));
		list.add(createJiraIssue(2, "", desc2));
		return list;
	}

	private JiraIssue createJiraIssue(int jiraIssueId, String summary, String description)
	{
		JiraIssue jiraIssue = new JiraIssue();
		jiraIssue.setJiraIssueId(jiraIssueId);
		jiraIssue.setSummary(summary);
		jiraIssue.setDescription(description);
		return jiraIssue;
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
	
	private void setWeights(double summWeight, double descWeigtht, double commWeight)
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
