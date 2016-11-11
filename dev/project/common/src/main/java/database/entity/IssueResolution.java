package database.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ISSUE_RESOLUTION")
public class IssueResolution {
	@Id
	@Column(name = "ISSUE_RESOLUTION_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int issueResoultionId;
	
	@Column(name="RESOLUTION_NAME")
	private String resolutionName;

}
