package database.manager;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import database.entity.Assignee;
import database.entity.IssueComment;
import database.entity.IssuePriority;
import database.entity.IssueReporter;
import database.entity.IssueResolution;
import database.entity.IssueStatus;
import database.entity.IssueType;
import database.entity.JiraIssue;

public class DatabaseManagerTest {
	
	@Test
	public void shouldPersistWorkCorrectly() throws Exception {
		//given
		JiraIssue jiraIssue = createJiraIssue();
		//when
		persistJiraIssueIntoDatabase(jiraIssue);
		//then
		checkForJiraIssueInDatabase(jiraIssue);
		
	}

	private void checkForJiraIssueInDatabase(JiraIssue jiraIssue) {
		//select jiraissue, check for all params
	}

	private void persistJiraIssueIntoDatabase(JiraIssue jiraIssue) {
		DatabaseManager.persist(jiraIssue);
	}

	private JiraIssue createJiraIssue() {
		IssueReporter issueReporter = createIssueReporter();
		IssueType issueType = createIssueType();
		Set<IssueComment> issueComment = createIssueComments();
		Set<Assignee> assignees = createAssignees();
		IssueResolution issueResolution = createIssueResolution();
		IssueStatus issueStatus = createIssueStatus();
		IssuePriority issuePriority = createIssuePriority();
		
		JiraIssue jiraIssue = new JiraIssue();
		jiraIssue.setReporter(issueReporter);
		jiraIssue.setIssueType(issueType);
//		jiraIssue.setComments(issueComment);
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

	private Set<IssueComment> createIssueComments() {
		Set<IssueComment> issueComment = new HashSet<>();
		issueComment.add(createIssueComment());
		issueComment.add(createIssueComment());
		return issueComment;
	}

	private IssueComment createIssueComment() {
		IssueComment issueComment = new IssueComment();
		issueComment.setContent("Content");
		return issueComment;
	}

	private IssueType createIssueType() {
		IssueType issueType = new IssueType();
		issueType.setTypeName("BUG");
		return issueType;
	}

	private IssueReporter createIssueReporter() {
		IssueReporter issueReporter = new IssueReporter();
		issueReporter.setFullName("Issue reporter full name");
		return issueReporter;
	}

}
