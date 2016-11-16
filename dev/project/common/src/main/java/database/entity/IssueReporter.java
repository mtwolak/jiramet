package database.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ISSUE_REPORTER")
public class IssueReporter {

	@Id
	@Column(name = "ISSUE_REPORTER_ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int issueReportedId;

	@Column(name = "FULL_NAME", nullable = false)
	private String fullName;

	public String getFullName() {
		return fullName;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IssueReporter other = (IssueReporter) obj;
		if (fullName == null) {
			if (other.fullName != null)
				return false;
		} else if (!fullName.equals(other.fullName))
			return false;
		return true;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getIssueReportedId() {
		return issueReportedId;
	}
}
