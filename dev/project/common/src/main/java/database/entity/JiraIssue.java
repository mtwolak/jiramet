package database.entity;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "JIRA_ISSUE")
public class JiraIssue {
	@Id
	@Column(name = "JIRA_ISSUE_ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int jiraIssueId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "PROJECT_ID")
	private JiraProject jiraProject;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ISSUE_REPORTER_ID")
	private IssueReporter issueReporter;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ISSUE_TYPE_ID")
	private IssueType issueType;

	@OneToMany(targetEntity = IssueComment.class, mappedBy = "jiraIssueNew")
	private Set<IssueComment> issueComments;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ISSUE_RESOLUTION_ID")
	private IssueResolution issueResolution;

	@Enumerated(EnumType.STRING)
	@Column(name="ISSUE_STATUS")
	private IssueStatus issueStatus;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ISSUE_PRIORITY_ID")
	private IssuePriority issuePriority;

	@Column(name = "CODE", nullable = false)
	private String code;

	@Column(name = "CREATED_AT", nullable = true)
	private Timestamp createdAt = null;

	@Column(name = "FIRST_RESPONSE_DATE", nullable = true)
	private Timestamp firstResponseDate = null;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "PURPOSE")
	private String purpose = "T";

	@OneToMany(cascade=CascadeType.ALL, mappedBy="jiraIssue", fetch=FetchType.EAGER)
	private Set<AssignedIssue> assignedIssues;

	public JiraProject getJiraProject() {
		return jiraProject;
	}

	public void setJiraProject(JiraProject jiraProject) {
		this.jiraProject = jiraProject;
	}

	public IssueReporter getIssueReporter() {
		return issueReporter;
	}

	public void setIssueReporter(IssueReporter issueReporter) {
		this.issueReporter = issueReporter;
	}

	public IssueType getIssueType() {
		return issueType;
	}

	public void setIssueType(IssueType issueType) {
		this.issueType = issueType;
	}

	public Set<IssueComment> getIssueComments() {
		return issueComments;
	}

	public void setIssueComments(Set<IssueComment> issueComments) {
		this.issueComments = issueComments;
	}

	public IssueResolution getIssueResolution() {
		return issueResolution;
	}

	public void setIssueResolution(IssueResolution issueResolution) {
		this.issueResolution = issueResolution;
	}

	public IssueStatus getIssueStatus() {
		return issueStatus;
	}

	public void setIssueStatus(IssueStatus issueStatus) {
		this.issueStatus = issueStatus;
	}

	public IssuePriority getIssuePriority() {
		return issuePriority;
	}

	public void setIssuePriority(IssuePriority issuePriority) {
		this.issuePriority = issuePriority;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getFirstResponseDate() {
		return firstResponseDate;
	}

	public void setFirstResponseDate(Timestamp firstResponseDate) {
		this.firstResponseDate = firstResponseDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public Set<AssignedIssue> getAssignedIssues() {
		return assignedIssues;
	}

//	public void setAssignedIssues(Set<AssignedIssue> assignedIssues) {
//		this.assignedIssues = assignedIssues;
//	}


}
