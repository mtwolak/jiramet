package database.application;

import static org.junit.Assert.*;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import database.entity.JiraIssue;
import database.entity.JiraProject;
import database.manager.DataBaseType;
import database.manager.DatabaseManager;

public class DatabaseApplicationTest
{

	private final static int ISSUE_ID = 1;
	private DatabaseManager dbm;
	private Session session;
	private Criteria criteria;

	@Before
	public void setUp() {
		dbm = new DatabaseManager(DataBaseType.TEST);
		dbm.init();
		session = dbm.getSession();
	}

	@After
	public void close() {
		dbm.close();
	}

	@Test
	public void getJiraIssueTest() {
		criteria = session.createCriteria(JiraIssue.class);
		JiraIssue issue = (JiraIssue) criteria.add(Restrictions.eq("id", ISSUE_ID)).list().get(0);
		assertNotNull(issue);
	}

	@Test
	public void getJiraProjectTest() {
		criteria = session.createCriteria(JiraIssue.class);
		JiraIssue issue = (JiraIssue) criteria.add(Restrictions.eq("id", ISSUE_ID)).list().get(0);
		int project = issue.getJiraProject().getJiraProjectId();
		criteria = session.createCriteria(JiraProject.class);
		JiraProject projectFromDB = (JiraProject) criteria.add(Restrictions.eq("id", project)).list().get(0);
		assertSame(project, projectFromDB.getJiraProjectId());
	}

	@Test
	public void getJiraProjectsTest() {
		criteria = session.createCriteria(JiraProject.class);
		@SuppressWarnings("rawtypes")
		List projects = criteria.list();
		assertNotNull(projects);
	}

	@Test
	public void getJiraIssuesTest() {
		criteria = session.createCriteria(JiraIssue.class);
		JiraIssue issue = (JiraIssue) criteria.add(Restrictions.eq("id", ISSUE_ID)).list().get(0);
		JiraProject project = issue.getJiraProject();
		criteria = session.createCriteria(JiraIssue.class);
		criteria.add(Restrictions.eq("jiraProject", project));
		@SuppressWarnings("rawtypes")
		List issues = criteria.list();
		assertNotNull(issues);
	}

	@Test
	public void closeSessionTest() {
		session.close();
		assertEquals("SessionImpl(<closed>)", session.toString());
	}

}
