package jira.connector;

import static org.junit.Assert.*;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.util.concurrent.Promise;

import database.entity.JiraIssue;
import database.entity.JiraProject;
import database.manager.DataBaseTestHelper;
import database.manager.DataBaseType;

@Ignore
public class IssueDownloaderTest
{

	private IssueDownloader issueDownloader;
	private JiraUtil jiraUtil;
	private final static int issuesCount = 1;

	@Before
	public void setUp()
	{
		issueDownloader = new IssueDownloader(DataBaseType.TEST);
		jiraUtil = issueDownloader.getJiraUtil();
		issueDownloader.getIssueDbContext().initDbm();
		truncateAllTables();
	}

	@After
	public void close()
	{
		truncateAllTables();
		issueDownloader.getIssueDbContext().closeDbm();
	}

	@Test
	public void testAddedIssuesSpring()
	{
		addIssuesFromSpringProject();
		int springCount = getIssuesCount("Spring Framework");
		assertEquals(springCount, issuesCount);
	}

	@Test
	public void testAddedIssuesMongoDB()
	{
		addIssuesFromMongoDbProject();
		int mongoCount = getIssuesCount("MongoDB Server");
		assertEquals(mongoCount, issuesCount);
	}

	@Test
	public void testAddedIssuesCamunda()
	{
		addIssuesFromCamundaProject();
		int camudaCount = getIssuesCount("Camunda BPM");
		assertEquals(camudaCount, issuesCount);
	}
	
	private void truncateAllTables() {
		DataBaseTestHelper.truncateAllTables(issueDownloader.getIssueDbContext().getDbm());
	}

	private void addIssuesFromSpringProject()
	{
		Promise<SearchResult> springRes = jiraUtil.getIssuesFromSpringProject(0, issuesCount);
		issueDownloader.addIssuesFromProject(springRes, JiraUtil.JIRA_SPRING_FRAMEWORK_PROJECTKEY);
	}

	private void addIssuesFromMongoDbProject()
	{
		Promise<SearchResult> mongoRes = jiraUtil.getIssuesFromMongoDBProject(0, issuesCount);
		issueDownloader.addIssuesFromProject(mongoRes, JiraUtil.JIRA_MONGODB_PROJECTKEY);

	}

	private void addIssuesFromCamundaProject()
	{
		Promise<SearchResult> camundaRes = jiraUtil.getIssuesFromCamundaProject(0, issuesCount);
		issueDownloader.addIssuesFromProject(camundaRes, JiraUtil.JIRA_CAMUNDA_PROJECTKEY);

	}

	@SuppressWarnings("rawtypes")
	private int getIssuesCount(String projectName)
	{
		JiraProject project = issueDownloader.getIssueDbContext().getJiraProject(projectName);
		Session session = issueDownloader.getIssueDbContext().getDbm().getSession();
		Criteria criteria = session.createCriteria(JiraIssue.class);
		criteria.add(Restrictions.eq("jiraProject", project));
		List issues = criteria.list();
		session.close();

		return issues.size();
	}

}
