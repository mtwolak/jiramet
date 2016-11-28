package prediction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class IssueResolvingTimesTest
{

	private TestDataHolder testData;
	private IssueResolvingTimes irt;
	private double prediction1;
	private double prediction2;
	private double prediction3;

	@Before
	public void setUp()
	{
		irt = new IssueResolvingTimes();
		testData = new TestDataHolder();
	}

	@Test
	public void testGetIssueResolvingTimePrediction()
	{
		assertNotNull(irt.getIssueResolvingTimePrediction(testData.getIssueSimilarities()));
	}
	
	@Test
	public void testGetIssueResolvingTimePredictionSize()
	{
		assertEquals(irt.getIssueResolvingTimePrediction(testData.getIssueSimilarities()).size(), 3);
	}
	
	@Test
	public void testPrediction1()
	{
		prediction1 = irt.getIssueResolvingTimePrediction(testData.getIssueSimilarities()).get(0).getPredictedTime();
		assertTrue(prediction1 == 3.9 || prediction1 == 3.33 || prediction1 == 2.0);
	}
	
	@Test
	public void testPrediction2()
	{
		prediction2 = irt.getIssueResolvingTimePrediction(testData.getIssueSimilarities()).get(1).getPredictedTime();
		assertTrue(prediction2 == 3.9 || prediction2 == 3.33 || prediction2 == 2.0);
	}
	
	@Test
	public void testPrediction3()
	{
		prediction3 = irt.getIssueResolvingTimePrediction(testData.getIssueSimilarities()).get(2).getPredictedTime();
		assertTrue(prediction3 == 3.9 || prediction3 == 3.33 || prediction3 == 2.0);
	}


}
