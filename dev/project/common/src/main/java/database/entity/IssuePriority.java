package database.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ISSUE_PRIORITY")
public class IssuePriority {
	@Id
	@Column(name = "ISSUE_PRIORITY_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int issuePriorityId;
	
	@Column(name="PRIORITY_NAME")
	private String priorityName;

}
