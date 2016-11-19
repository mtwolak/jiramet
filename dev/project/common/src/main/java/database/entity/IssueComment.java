package database.entity;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ISSUE_COMMENT")
public class IssueComment {
	@Id
	@Column(name = "ISSUE_COMMENT_ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int issueCommentId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "JIRA_ISSUE_ID")
	private JiraIssue jiraIssueNew;

	@Column(name = "CONTENT")
	private String content;
	@Column(name = "ADDED_AT", nullable = true)
	private Timestamp addedAt = null;
	@Column(name = "ADDED_BY")
	private String addedBy;

	public JiraIssue getJiraIssueNew() {
		return jiraIssueNew;
	}
	
	public void setJiraIssue(JiraIssue jiraIssueNew) {
		this.jiraIssueNew = jiraIssueNew;
	}

	public int getIssueCommentId() {
		return issueCommentId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getAddedAt() {
		return addedAt;
	}

	public void setAddedAt(Timestamp addedAt) {
		this.addedAt = addedAt;
	}

	public String getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}

}
