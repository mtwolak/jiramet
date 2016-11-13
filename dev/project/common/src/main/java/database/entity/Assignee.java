package database.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ASSIGNEE")
public class Assignee {
	
	@Id
	@Column(name = "ASSIGNEE_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int assigneeId;
	
	@Column(name="NAME")
	private String name;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy="assignees")
	private Set<JiraIssue> jiraIssues;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<JiraIssue> getJiraIssues() {
		return jiraIssues;
	}

	public void setJiraIssues(Set<JiraIssue> jiraIssues) {
		this.jiraIssues = jiraIssues;
	}

	public int getAssigneeId() {
		return assigneeId;
	}
}
