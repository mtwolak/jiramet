package similarity;

import static org.junit.Assert.*;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import database.entity.JiraIssue;
import database.manager.DataBaseType;
import database.manager.DatabaseManager;
import javafx.util.Pair;

public class IssuesSimilarityTest
{

	private final static int ISSUE_ID = 1;
	private final static int ISSUE2_ID = 2;
	private DatabaseManager dbm;
	private Session session;
	private Criteria criteria;
	private JiraIssue issue;
	private JiraIssue issue2;
	private IssuesSimilarity is;
	private List<Pair<Integer, Double>> similarityList;

	@Before
	public void setUp() {
		dbm = new DatabaseManager(DataBaseType.TEST);
		dbm.init();
		session = dbm.getSession();
		criteria = session.createCriteria(JiraIssue.class);
		issue = (JiraIssue) criteria.add(Restrictions.eq("id", ISSUE_ID)).list().get(0);
		criteria = session.createCriteria(JiraIssue.class);
		issue2 = (JiraIssue) criteria.add(Restrictions.eq("id", ISSUE2_ID)).list().get(0);
		is = new IssuesSimilarity();
	}

	@After
	public void close() {
		dbm.close();
	}

	@Test
	public void getSimilarityListTest() {
		similarityList = is.getIssueSimilarityList(issue);
		assertNotNull(similarityList);
	}

	@Test
	public void getSimilarityListTest2() {
		similarityList = is.getIssueSimilarityList(issue);
		assertFalse(similarityList.get(0).getKey() == ISSUE_ID);
	}

	@Test
	public void getIssuesSimilarityTest() {
		double similarity = is.getIssuesSimilarity(issue, issue2);
		assertTrue(similarity >= 0 && similarity <= 1);
	}

	@Test
	public void getIssuesSummariesSimilarityTest() {
		double similarity = is.getIssuesSummariesSimilarity(issue, issue2);
		assertTrue(similarity >= 0 && similarity <= 1);
	}

	@Test
	public void getIssuesDescriptionsSimilarityTest() {
		double similarity = is.getIssuesDescriptionsSimilarity(issue, issue2);
		assertTrue(similarity >= 0 && similarity <= 1);
	}

	@Test
	public void getIssuesCommentsSimilarityTest() {
		double similarity = is.getIssuesCommentsSimilarity(issue, issue2);
		assertTrue(similarity >= 0 && similarity <= 1);
	}

	@Test
	public void collectIssueCommentsTest() {
		assertNotNull(is.collectIssueComments(issue));
	}
}
