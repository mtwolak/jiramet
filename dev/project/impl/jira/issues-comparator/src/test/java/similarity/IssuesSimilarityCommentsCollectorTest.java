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
public class IssuesSimilarityCommentsCollectorTest
{

	@Mock
	private IssuesSimilarityCommentsCollector ish;

	@Before
	public void setUp() {
		ish = Mockito.mock(IssuesSimilarityCommentsCollector.class);
	}

	@Test
	public void collectIssueCommentsTest() {
		StringBuilder sb = new StringBuilder("test");
		Mockito.when(ish.collectIssueComments(Matchers.any(JiraIssue.class))).thenReturn(sb);
		assertNotNull(ish.collectIssueComments(Matchers.any(JiraIssue.class)));
	}

}
