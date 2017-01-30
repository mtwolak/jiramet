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
@Table(name = "jira_project")
public class JiraProject {
	@Id
	@Column(name = "JIRA_PROJECT_ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int jiraProjectId;

	@Column(name = "PROJECT_NAME", nullable = false)
	private String projectName;

	@OneToMany(targetEntity = JiraIssue.class, mappedBy = "jiraProject")
	private Set<JiraIssue> jiraIssues;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Set<JiraIssue> getJiraIssues() {
		return jiraIssues;
	}

	public void setJiraIssues(Set<JiraIssue> jiraIssues) {
		this.jiraIssues = jiraIssues;
	}

	public int getJiraProjectId() {
		return jiraProjectId;
	}

}
