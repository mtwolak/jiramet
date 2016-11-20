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

public class IssuesSimilarityTest {

	private final static int ISSUE_ID = 1;
	DatabaseManager dbm;
	Session session;
	Criteria criteria;
	JiraIssue issue;
	IssuesSimilarity is;
	List<Pair<Integer, Double>> similarityList;

	@Before
	public void setUp() {
		dbm = new DatabaseManager(DataBaseType.TEST);
		dbm.init();
		session = dbm.getSession();
		criteria = session.createCriteria(JiraIssue.class);
		issue = (JiraIssue) criteria.add(Restrictions.eq("id", ISSUE_ID)).list().get(0);
		is = new IssuesSimilarity(issue);
	}
	
	@After
	public void close() 
	{
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
		assertFalse(similarityList.get(0).getKey()==ISSUE_ID);
	}
	
	@Test
	public void getIssueSimilarityTest() 
	{
		double result = -1.0;
		assertNotEquals(result, -1.0);
	}

}
