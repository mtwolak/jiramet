package database.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "JIRA_ISSUE")
public class JiraIssue {
	@Id
	@Column(name = "JIRA_ISSUE_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int jiraIssueId;

	@OneToMany
	private JiraProject jiraProject;

	@ManyToOne(cascade = CascadeType.ALL)
	private IssueReporter issueReporter;
	@ManyToOne(cascade = CascadeType.ALL)
	private IssueType issueType;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "jiraIssue")
	private Set<IssueComment> issueComments;

	@OneToMany(cascade = CascadeType.ALL)
	private IssueResolution issueResolution;

	@OneToMany(cascade = CascadeType.ALL)
	private IssueStatus issueStatus;

	@OneToMany(cascade = CascadeType.ALL)
	private IssuePriority issuePriority;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "ASSIGNED_ISSUE", joinColumns = {
			@JoinColumn(name = "JIRA_ISSUE_ID", nullable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "ASSIGNEE_ID", nullable = false) })
	private Set<Assignee> assignees;

	public void setReporter(IssueReporter issueReporter) {
		this.issueReporter = issueReporter;
	}

	public void setIssueType(IssueType issueType) {
		this.issueType = issueType;
	}

	public void setComments(Set<IssueComment> issueComments) {
		this.issueComments = issueComments;
	}

	public void setAssignees(Set<Assignee> assignees) {
		this.assignees = assignees;
	}

	public void setResolution(IssueResolution issueResolution) {
		this.issueResolution = issueResolution;
	}

	public void setStatus(IssueStatus issueStatus) {
		this.issueStatus = issueStatus;
	}

	public void setPriority(IssuePriority issuePriority) {
		this.issuePriority = issuePriority;
	}

}
