package database.manager;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;

import static org.hamcrest.Matchers.*;

import database.entity.AssignedIssue;
import database.entity.Assignee;
import database.entity.IssuePriority;
import database.entity.IssueReporter;
import database.entity.IssueResolution;
import database.entity.IssueStatus;
import database.entity.IssueType;
import database.entity.JiraIssue;
import database.jira.JiraAssigneeLoader;
import database.jira.JiraIssueLoader;
import utils.properties.PropertiesReader;
import utils.properties.hibernate.HibernateTestConfiguration;

public class DatabaseManagerTest {

	private final static String REPORTER_NAME = "Marcin";
	private final static String ASSIGNEE_NAME = "Michal";
	private DatabaseManager databaseManager;
	
	@Mock
	private PropertiesReader propertiesReaderMock;

	@Before
	public void setUp() {
		databaseManager = new DatabaseManager(new HibernateTestConfiguration(propertiesReaderMock));
		databaseManager.init();
	}

	@After
	public void close() {
		truncateAllTables();
	}

	private void truncateAllTables() {
		DataBaseTestHelper.truncateAllTables(databaseManager);
	}

	@Test
	public void checkCreatingJiraIssueAndTheirAssignee() {
		//given
		persistJiraObjects();
		
		//when
		JiraIssueLoader jiraIssueLoader = new JiraIssueLoader(databaseManager, REPORTER_NAME);
		JiraIssue jiraIssueFromDb = jiraIssueLoader.get(JiraIssue.class);
		AssignedIssue assignedIssue = jiraIssueFromDb.getAssignedIssues().iterator().next();
		
		//then
		assertThat(assignedIssue.getAssignee().getName(), is(ASSIGNEE_NAME));
	}

	private void persistJiraObjects() {
		persistAssignee();
		persisJiraIssue();
		persistAssignedIssue();
	}

	private void persistAssignedIssue() {
		JiraIssue jiraIssueFromDb = new JiraIssueLoader(databaseManager, REPORTER_NAME).get(JiraIssue.class);
		Assignee assigneeFromDb = new JiraAssigneeLoader(databaseManager, ASSIGNEE_NAME).get(Assignee.class);
		AssignedIssue assignedIssue = createAssignedIssue(jiraIssueFromDb, assigneeFromDb);
		databaseManager.persist(assignedIssue);
		databaseManager.persist(jiraIssueFromDb);
	}

	private void persisJiraIssue() {
		JiraIssue jiraIssue = createJiraIssue();
		jiraIssue.getIssuePriority().setPriorityName("PriorityName2");
		jiraIssue.getIssueResolution().setResolutionName("Resolution name2");
		jiraIssue.getIssueType().setTypeName("Name");
		databaseManager.persist(jiraIssue);
	}

	private void persistAssignee() {
		databaseManager.persist(createAssignee(ASSIGNEE_NAME));
	}

	private AssignedIssue createAssignedIssue(JiraIssue jiraIssue, Assignee as) {
		AssignedIssue assignedIssue = new AssignedIssue();
		assignedIssue.setAssignee(as);
		assignedIssue.setJiraIssue(jiraIssue);
		return assignedIssue;
	}

	private Assignee createAssignee(String assigneeName) {
		Assignee assignee = new Assignee();
		assignee.setName(assigneeName);
		return assignee;
	}

	private JiraIssue createJiraIssue() {
		IssueReporter issueReporter = createIssueReporter();
		IssueType issueType = createIssueType();
		IssueResolution issueResolution = createIssueResolution();
		IssueStatus issueStatus = IssueStatus.CLOSED;
		IssuePriority issuePriority = createIssuePriority();
		return createJiraIssue(issueReporter, issueType, issueResolution, issueStatus, issuePriority);
	}

	private JiraIssue createJiraIssue(IssueReporter issueReporter, IssueType issueType, IssueResolution issueResolution,
			IssueStatus issueStatus, IssuePriority issuePriority) {
		JiraIssue jiraIssue = new JiraIssue();
		jiraIssue.setCode("Some Code");
		jiraIssue.setSummary("Some summary");
		jiraIssue.setIssueReporter(issueReporter);
		jiraIssue.setIssueType(issueType);
		jiraIssue.setIssueResolution(issueResolution);
		jiraIssue.setIssueStatus(issueStatus);
		jiraIssue.setIssuePriority(issuePriority);
		return jiraIssue;
	}

	private IssuePriority createIssuePriority() {
		IssuePriority issuePriority = new IssuePriority();
		issuePriority.setPriorityName("Name");
		return issuePriority;
	}

	private IssueResolution createIssueResolution() {
		IssueResolution issueResolution = new IssueResolution();
		issueResolution.setResolutionName("ResolutionName");
		return issueResolution;
	}

	private IssueType createIssueType() {
		IssueType issueType = new IssueType();
		issueType.setTypeName("BUG");
		return issueType;
	}

	private IssueReporter createIssueReporter() {
		IssueReporter issueReporter = new IssueReporter();
		issueReporter.setFullName(REPORTER_NAME);
		return issueReporter;
	}

}
