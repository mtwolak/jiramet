package database.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "issue_priority")
public class IssuePriority {
	@Id
	@Column(name = "ISSUE_PRIORITY_ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int issuePriorityId;

	@Column(name = "PRIORITY_NAME", nullable = false, unique = true)
	private String priorityName;

	@OneToMany(targetEntity = JiraIssue.class, mappedBy = "issuePriority")
	private Set<JiraIssue> jiraIssues;

	public String getPriorityName() {
		return priorityName;
	}

	public void setPriorityName(String priorityName) {
		this.priorityName = priorityName;
	}

	public Set<JiraIssue> getJiraIssues() {
		return jiraIssues;
	}

	public void setJiraIssues(Set<JiraIssue> jiraIssues) {
		this.jiraIssues = jiraIssues;
	}

	public int getIssuePriorityId() {
		return issuePriorityId;
	}
}
