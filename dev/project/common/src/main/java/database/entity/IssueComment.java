package database.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ISSUE_COMMENT")
public class IssueComment {
	@Id
	@Column(name = "ISSUE_COMMENT_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int issueCommentId;

	@Column(name = "CONTENT")
	private String content;
	@Column(name = "ADDED_AT")
	private Date addedAt;
	@Column(name = "ADDED_BY")
	private String addedBy;

	public void setContent(String content) {
		this.content = content;

	}

}
