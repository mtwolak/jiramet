package database.manager;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import database.entity.Assignee;
import database.entity.IssueComment;
import database.entity.IssuePriority;
import database.entity.IssueReporter;
import database.entity.IssueResolution;
import database.entity.IssueStatus;
import database.entity.IssueType;
import database.entity.JiraIssue;
import database.jira.JiraIssueLoader;

public class DatabaseManagerTest {
	
	private final static String REPORTER_NAME = "Marcin";
	private DatabaseManager databaseManager;
	private JiraIssueLoader jiraIssueLoader;
	
	@Before
	public void setUp() {
		databaseManager = new DatabaseManager();
		jiraIssueLoader = new JiraIssueLoader();
		databaseManager.init();
	}
	
	@After
	public void close() {
		databaseManager.close();
	}
	
	@Test
	public void createJiraIssueWithComment() throws Exception {
		persistJiraIssue();
		persistJiraComment();
		//todo: check, if entry exists in DB

	}

	private void persistJiraComment() {
		JiraIssue issue = jiraIssueLoader.loadByReporter(databaseManager, REPORTER_NAME);
		IssueComment comment = new IssueComment();
		comment.setJiraIssue(issue);
		databaseManager.persist(comment);
	}

	private void persistJiraIssue() {
		JiraIssue jiraIssue = createJiraIssue();
		databaseManager.persist(jiraIssue);
	}



	private JiraIssue createJiraIssue() {
		IssueReporter issueReporter = createIssueReporter();
		IssueType issueType = createIssueType();
		Set<Assignee> assignees = createAssignees();
		IssueResolution issueResolution = createIssueResolution();
		IssueStatus issueStatus = createIssueStatus();
		IssuePriority issuePriority = createIssuePriority();
		
		JiraIssue jiraIssue = new JiraIssue();
		jiraIssue.setReporter(issueReporter);
		jiraIssue.setIssueType(issueType);
		jiraIssue.setAssignees(assignees);
		jiraIssue.setResolution(issueResolution);
		jiraIssue.setStatus(issueStatus);
		jiraIssue.setPriority(issuePriority);
		
		return jiraIssue;
	}

	private IssuePriority createIssuePriority() {
		return new IssuePriority();
	}

	private IssueStatus createIssueStatus() {
		return new IssueStatus();
	}

	private IssueResolution createIssueResolution() {
		return new IssueResolution();
	}

	private Set<Assignee> createAssignees() {
		HashSet<Assignee> assignees = new HashSet<Assignee>();
		assignees.add(createAssignee());
		assignees.add(createAssignee());
		return assignees;
	}

	private Assignee createAssignee() {
		Assignee assignee = new Assignee();
		assignee.setName("assignee name");
		return assignee;
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
