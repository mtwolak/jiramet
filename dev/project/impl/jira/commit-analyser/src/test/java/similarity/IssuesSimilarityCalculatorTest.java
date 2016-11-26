package similarity;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import database.entity.JiraIssue;
import jira.JiraIssueSimilarity;

@RunWith(MockitoJUnitRunner.class)
public class IssuesSimilarityCalculatorTest
{
	@Mock
	private IssuesSimilarityCalculator isc;
	private double similarity;

	@Before
	public void setUp() {
		isc = Mockito.mock(IssuesSimilarityCalculator.class);
	}

	@Test
	public void getSimilarityListTest() {
		List<JiraIssueSimilarity> similarityList = new ArrayList<JiraIssueSimilarity>();
		Mockito.when(isc.getIssuesSimilarityList(Matchers.any(JiraIssue.class))).thenReturn(similarityList);
	}

	@Test
	public void getSimilarityListTest2() {
		assertNotNull(isc.getIssuesSimilarityList(Matchers.any(JiraIssue.class)));
	}

	@Test
	public void getIssuesSimilarityTest() {
		assertNotNull(isc.getIssuesSimilarity(Matchers.any(JiraIssue.class), Matchers.any(JiraIssue.class)));
	}

	@Test
	public void getIssuesSummariesSimilarityTest() {
		similarity = isc.getIssuesSummariesSimilarity(Matchers.any(JiraIssue.class), Matchers.any(JiraIssue.class));
		assertTrue(similarity >= 0 && similarity <= 1);
	}

	@Test
	public void getIssuesDescriptionsSimilarityTest() {
		similarity = isc.getIssuesDescriptionsSimilarity(Matchers.any(JiraIssue.class), Matchers.any(JiraIssue.class));
		assertTrue(similarity >= 0 && similarity <= 1);
	}

	@Test
	public void getIssuesCommentsSimilarityTest() {
		similarity = isc.getIssuesCommentsSimilarity(Matchers.any(JiraIssue.class), Matchers.any(JiraIssue.class));
		assertTrue(similarity >= 0 && similarity <= 1);
	}

}
