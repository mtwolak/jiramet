package database.entity.dto;

import java.sql.Timestamp;

public class IssueCommentDTO
{
	private int issueCommentId;
	private JiraIssueDTO jiraIssueNew;
	private String content;
	private Timestamp addedAt = null;
	private String addedBy;
	
	public int getIssueCommentId()
	{
		return issueCommentId;
	}
	public void setIssueCommentId(int issueCommentId)
	{
		this.issueCommentId = issueCommentId;
	}
	public JiraIssueDTO getJiraIssueNew()
	{
		return jiraIssueNew;
	}
	public void setJiraIssueNew(JiraIssueDTO jiraIssueNew)
	{
		this.jiraIssueNew = jiraIssueNew;
	}
	public String getContent()
	{
		return content;
	}
	public void setContent(String content)
	{
		this.content = content;
	}
	public Timestamp getAddedAt()
	{
		return addedAt;
	}
	public void setAddedAt(Timestamp addedAt)
	{
		this.addedAt = (Timestamp) addedAt.clone();
	}
	public String getAddedBy()
	{
		return addedBy;
	}
	public void setAddedBy(String addedBy)
	{
		this.addedBy = addedBy;
	}
	
	
}
