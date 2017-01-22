package prediction;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import database.entity.AssignedIssue;
import database.entity.JiraIssue;
import jira.JiraIssueSimilarity;
import utils.converter.DateConverter;

public class IssueResolvingTimeCalculatorTest
{
	
	private IssueResolvingTimeCalculator irtc;
	private JiraIssueSimilarity testSimilarity;
	@Mock
	private JiraIssue testIssue;
	@Mock
	private AssignedIssue testAssignedIssue;

	@Before
	public void setUp()
	{
		irtc = new IssueResolvingTimeCalculator();
		prepareTestIssue();
		testSimilarity = new JiraIssueSimilarity(testIssue, 0.5);
	}
	
	private void prepareTestIssue()
	{
		MockitoAnnotations.initMocks(this);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Timestamp createDate = DateConverter.convertStringToTimestamp("2016-11-26 21:00", format);
		Timestamp resolveDate = DateConverter.convertStringToTimestamp("2016-11-28 21:00", format);
		Set<AssignedIssue> testSet = new HashSet<>();
		testSet.add(testAssignedIssue);
		Mockito.when(testAssignedIssue.getResolvedAt()).thenReturn(resolveDate);
		Mockito.when(testAssignedIssue.getJiraIssue()).thenReturn(testIssue);
		Mockito.when(testAssignedIssue.getJiraIssue().getCreatedAt()).thenReturn(createDate);
		Mockito.when(testIssue.getAssignedIssues()).thenReturn(testSet);
		
	}

	@Test
	public void testGetIssuesResolvingTimeNull()
	{
		double result = irtc.getIssuesResolvingTime(null);
		assertTrue(result == -1);
	}
	
	@Test
	public void testGetIssuesResolvingTimeEmpty()
	{
		double result = irtc.getIssuesResolvingTime(new ArrayList<JiraIssueSimilarity>());
		assertTrue(result == -1);
	}
	
	@Test
	public void testGetIssuesResolvingTime()
	{
		List<JiraIssueSimilarity> similarities = new ArrayList<>();
		similarities.add(testSimilarity);
		double result = irtc.getIssuesResolvingTime(similarities);
		assertTrue(result > 0);
	}

}
