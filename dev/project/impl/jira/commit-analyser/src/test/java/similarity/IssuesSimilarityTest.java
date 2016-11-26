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
public class IssuesSimilarityTest
{
	@Mock
	private IssuesSimilarity is;
	private double similarity;

	@Before
    public void setUp() {
        is = Mockito.mock(IssuesSimilarity.class);
    } 
	
	@Test
	public void getSimilarityListTest() {
		List<JiraIssueSimilarity> similarityList = new ArrayList<JiraIssueSimilarity>();
		Mockito.when(is.getIssueSimilarityList(Matchers.any(JiraIssue.class))).thenReturn(similarityList);
	}
	
	@Test
	public void getSimilarityListTest2() {
		assertNotNull(is.getIssueSimilarityList(Matchers.any(JiraIssue.class)));
	}

	@Test
	public void getIssuesSimilarityTest() {
		assertNotNull(is.getIssuesSimilarity(Matchers.any(JiraIssue.class), Matchers.any(JiraIssue.class)));
	}

	@Test
	public void getIssuesSummariesSimilarityTest() {
		similarity = is.getIssuesSummariesSimilarity(Matchers.any(JiraIssue.class), Matchers.any(JiraIssue.class));
		assertTrue(similarity >= 0 && similarity <= 1);
	}

	@Test
	public void getIssuesDescriptionsSimilarityTest() {
		similarity = is.getIssuesDescriptionsSimilarity(Matchers.any(JiraIssue.class), Matchers.any(JiraIssue.class));
		assertTrue(similarity >= 0 && similarity <= 1);
	}

	@Test
	public void getIssuesCommentsSimilarityTest() {
		similarity = is.getIssuesCommentsSimilarity(Matchers.any(JiraIssue.class), Matchers.any(JiraIssue.class));
		assertTrue(similarity >= 0 && similarity <= 1);
	}

	@Test
	public void collectIssueCommentsTest() {
		StringBuilder sb = new StringBuilder("test");
		Mockito.when(is.collectIssueComments(Matchers.any(JiraIssue.class))).thenReturn(sb);
		assertNotNull(is.collectIssueComments(Matchers.any(JiraIssue.class)));
	}
}
