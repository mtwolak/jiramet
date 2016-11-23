package prediction;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import database.application.DatabaseApplication;
import database.entity.JiraIssue;
import database.entity.JiraProject;
import database.manager.DataBaseType;
import database.manager.DatabaseManager;
import javafx.util.Pair;

public class IssueResolvingTimesTest {
	
	private final static int ISSUE_ID = 1;
	private final static int ASSIGNEE_ID = 1;
	private DatabaseManager dbm;
	private JiraIssue issue;
	private List<Pair<Integer, Double>> similarityList;
	private IssueResolvingTimes irt;
	
	@Before
	public void setUp() {
		dbm = new DatabaseManager(DataBaseType.TEST);
		dbm.init();
		Session session = dbm.getSession();
		Criteria criteria = session.createCriteria(JiraIssue.class);
		issue = (JiraIssue) criteria.add(Restrictions.eq("id", ISSUE_ID)).list().get(0);
		similarityList = new ArrayList<Pair<Integer, Double>>();
		irt = new IssueResolvingTimes();
		for (int i=1; i<50; i++)
		{
			if (i != issue.getJiraIssueId())
				similarityList
						.add(new Pair<Integer, Double>(i, Math.random()));
		}
	}

	@After
	public void close() {
		dbm.close();
	}
	
	@Test
	public void getIssueResolvingTimesTest() {
		List<Pair<Integer, Double>> issueResolvingTimesPrediction = irt.getIssueResolvingTimesPrediction(issue, similarityList);
		assertNotNull(issueResolvingTimesPrediction);
	}

	@Test
	public void getAssigneeSimilaritiesTest() throws Exception {
		List<Pair<Integer, Double>> assigneeSimilarities = irt.getAssigneeSimilarities(issue.getJiraProject(), ASSIGNEE_ID, similarityList);
		assertNotNull(assigneeSimilarities);
	}

	@Test
	public void getIssuesResolvingTimesTest() throws Exception {
		List<Pair<Integer, Double>> issuesResolvingTimes = irt.getIssuesResolvingTimes(issue.getJiraProject(), ASSIGNEE_ID);
		assertNotNull(issuesResolvingTimes);
	}

}
