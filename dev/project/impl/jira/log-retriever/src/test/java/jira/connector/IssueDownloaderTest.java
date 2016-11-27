package jira.connector;

import static org.junit.Assert.*;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.util.concurrent.Promise;

import database.entity.JiraIssue;
import database.entity.JiraProject;
import database.manager.DataBaseTestHelper;
import database.manager.DatabaseManager;
import jira.data.ProjectData;
import utils.properties.hibernate.HibernateTestConfiguration;

public class IssueDownloaderTest
{

	private IssueDownloader issueDownloader;
	private DatabaseManager dbManager;
	private JiraUtil jiraUtil;
	private final static int issuesCount = 1;

	@Before
	public void setUp()
	{
		dbManager = new DatabaseManager(new HibernateTestConfiguration());
		dbManager.init();
		issueDownloader = new IssueDownloader(new HibernateTestConfiguration());
		jiraUtil = new JiraUtil();
		issueDownloader.initIssueDbContext();
		truncateAllTables();
	}

	@After
	public void close()
	{
		truncateAllTables();
		issueDownloader.closeIssueDbContext();
		dbManager.close();
	}

	@Test
	public void testAddedIssuesSpring()
	{
		addIssuesFromSpringProject();
		int springCount = getIssuesCount(ProjectData.SPRING);
		assertEquals(springCount, issuesCount);
	}

	@Test
	public void testAddedIssuesMongoDB()
	{
		addIssuesFromMongoDbProject();
		int mongoCount = getIssuesCount(ProjectData.MONGODB);
		assertEquals(mongoCount, issuesCount);
	}

	@Test
	public void testAddedIssuesCamunda()
	{
		addIssuesFromCamundaProject();
		int camudaCount = getIssuesCount(ProjectData.CAMUNDA);
		assertEquals(camudaCount, issuesCount);
	}

	private void truncateAllTables()
	{
		DataBaseTestHelper.truncateAllTables(dbManager);
	}

	private void addIssuesFromSpringProject()
	{
		Promise<SearchResult> springRes = jiraUtil.getIssuesFromProject(ProjectData.SPRING, 0, issuesCount);
		issueDownloader.addIssuesFromProject(springRes, ProjectData.SPRING);
	}

	private void addIssuesFromMongoDbProject()
	{
		Promise<SearchResult> mongoRes = jiraUtil.getIssuesFromProject(ProjectData.MONGODB, 0, issuesCount);
		issueDownloader.addIssuesFromProject(mongoRes, ProjectData.MONGODB);

	}

	private void addIssuesFromCamundaProject()
	{
		Promise<SearchResult> camundaRes = jiraUtil.getIssuesFromProject(ProjectData.CAMUNDA, 0, issuesCount);
		issueDownloader.addIssuesFromProject(camundaRes, ProjectData.CAMUNDA);

	}

	@SuppressWarnings("rawtypes")
	private int getIssuesCount(ProjectData project)
	{
		Session session = dbManager.getSession();
		Criteria criteriaProject = session.createCriteria(JiraProject.class);
		List projects = criteriaProject.add(Restrictions.eq("projectName", project.getProjectName())).list();
		if (!projects.isEmpty())
		{
			JiraProject jiraProject = (JiraProject) projects.get(0);
			Criteria criteriaIssues = session.createCriteria(JiraIssue.class);
			criteriaIssues.add(Restrictions.eq("jiraProject", jiraProject));
			List issues = criteriaIssues.list();
			session.close();
			return issues.size();
		}
		session.close();
		return 0;
	}

}
