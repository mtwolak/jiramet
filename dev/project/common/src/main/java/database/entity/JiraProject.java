package database.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "JIRA_PROJECT")
public class JiraProject {
	@Id
	@Column(name = "JIRA_PROJECT_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int jiraProjectId;
	
	@Column(name = "PROJECT_NAME")
	private String projectName;

}
