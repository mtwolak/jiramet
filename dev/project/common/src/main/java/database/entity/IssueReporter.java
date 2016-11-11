package database.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ISSUE_REPORTER")
public class IssueReporter {
	
	@Id
	@Column(name="ISSUE_REPORTER_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int issueReportedId;
	
	@Column(name="FULL_NAME")
	private String fullName;
	
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

}
