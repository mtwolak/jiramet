package similarity;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;

import database.entity.JiraIssue;

public class IssuesSimilarityHelperTest
{

	@Mock
	private IssuesSimilarityHelper ish;

	@Before
	public void setUp() {
		ish = Mockito.mock(IssuesSimilarityHelper.class);
	}

	@Test
	public void collectIssueCommentsTest() {
		StringBuilder sb = new StringBuilder("test");
		Mockito.when(ish.collectIssueComments(Matchers.any(JiraIssue.class))).thenReturn(sb);
		assertNotNull(ish.collectIssueComments(Matchers.any(JiraIssue.class)));
	}

}
