package prediction;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.*;

import static org.junit.Assert.*;

public class IssueResolvingTimesHelperTest
{
	IssueResolvingTimesHelper issueResolvingTimesHelper;
	TestDataHolder testData;

	@Before
	public void setUp()
	{
		issueResolvingTimesHelper = new IssueResolvingTimesHelper();
		testData = new TestDataHolder();
	}
	
	@Test
	public void testExtractAssignees()
	{
		assertTrue(issueResolvingTimesHelper.extractAssignees(testData.getIssueSimilarities()).size() == 4);
	}
	
	@Test
	public void testGetIssuesSimilaritiesForNull()
	{
		assertNull(issueResolvingTimesHelper.getIssuesSimilaritiesForAssignee(null, testData.getIssueSimilarities()));
	}

	@Test
	public void testGetIssuesSimilaritiesForDeveloper1()
	{
		assertTrue(issueResolvingTimesHelper.getIssuesSimilaritiesForAssignee(testData.getDeveloper1(), testData.getIssueSimilarities()).size() == 2);
	}
	
	@Test
	public void testGetIssuesSimilaritiesForDeveloper4()
	{
		assertTrue(issueResolvingTimesHelper.getIssuesSimilaritiesForAssignee(testData.getDeveloper4(), testData.getIssueSimilarities()).size() == 0);
	}

	@Test
	public void testGetIssuesResolvingTime()
	{
		assertThat(issueResolvingTimesHelper.getIssuesResolvingTime(testData.getDeveloper1TopSimilarities()), instanceOf(Double.class));
	}


}
