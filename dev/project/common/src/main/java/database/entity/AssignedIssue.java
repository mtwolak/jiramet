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
@Table(name = "ASSIGNED_ISSUE")
public class AssignedIssue
{

	@Id
	@Column(name = "ASSIGNED_ISSUE_ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int assignedIssueId;

	@ManyToOne
	@JoinColumn(name = "JIRA_ISSUE_ID", nullable = false)
	private JiraIssue jiraIssue;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ASSIGNEE_ID", nullable = false)
	private Assignee assignee;

	@Column(name = "RESOLVED_AT", nullable = true)
	private Timestamp resolvedAt = null;

	public JiraIssue getJiraIssue()
	{
		return jiraIssue;
	}

	public void setJiraIssue(JiraIssue jiraIssue)
	{
		this.jiraIssue = jiraIssue;
	}

	public Assignee getAssignee()
	{
		return assignee;
	}

	public void setAssignee(Assignee assignee)
	{
		this.assignee = assignee;
	}

	public Timestamp getResolvedAt()
	{
		return resolvedAt;
	}

	public void setResolvedAt(Timestamp resolvedAt)
	{
		this.resolvedAt = resolvedAt;
	}

	public int getAssignedIssueId()
	{
		return assignedIssueId;
	}
}
