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
@Table(name = "ISSUE_RESOLUTION")
public class IssueResolution {
	@Id
	@Column(name = "ISSUE_RESOLUTION_ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int issueResoultionId;

	@Column(name = "RESOLUTION_NAME", nullable = false, unique = true)
	private String resolutionName;

	@OneToMany(targetEntity = JiraIssue.class, mappedBy = "issueResolution")
	private Set<JiraIssue> jiraIssue;

	public String getResolutionName() {
		return resolutionName;
	}

	public void setResolutionName(String resolutionName) {
		this.resolutionName = resolutionName;
	}

	public Set<JiraIssue> getJiraIssue() {
		return jiraIssue;
	}

	public void setJiraIssue(Set<JiraIssue> jiraIssue) {
		this.jiraIssue = jiraIssue;
	}

	public int getIssueResoultionId() {
		return issueResoultionId;
	}

}
