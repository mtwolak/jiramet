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
@Table(name = "ISSUE_STATUS")
public class IssueStatus {
	
	@Id
	@Column(name = "ISSUE_STATUS_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="STATUS_NAME")
	private String statusName;
	 
	@OneToMany(targetEntity=JiraIssue.class, mappedBy="issueStatus")
	private Set<JiraIssue> jiraIssues;

}
