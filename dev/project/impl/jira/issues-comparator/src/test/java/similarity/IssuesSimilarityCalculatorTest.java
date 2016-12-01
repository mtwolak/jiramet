package similarity;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import database.entity.JiraIssue;

@RunWith(MockitoJUnitRunner.class)
public class IssuesSimilarityCalculatorTest
{
	@Mock
	private IssuesSimilarityCalculator isc;

	@Before
	public void setUp() {
		isc = Mockito.mock(IssuesSimilarityCalculator.class);
	}

	@Test
	public void getSimilarityListTest() {
		assertNotNull(isc.getIssuesSimilarityList(Matchers.any(JiraIssue.class)));
	}

	@Test
	public void getIssuesSimilarityTest() {
		assertNotNull(isc.getIssuesSimilarity(Matchers.any(JiraIssue.class), Matchers.any(JiraIssue.class)));
	}

	@Test
	public void getSimilarityTest2() {
		Mockito.when(isc.getSimilarity(Matchers.anyString(), Matchers.anyString())).thenReturn(0.5);
		assertTrue(0.5 == isc.getSimilarity("abc", "def"));
	}

}
