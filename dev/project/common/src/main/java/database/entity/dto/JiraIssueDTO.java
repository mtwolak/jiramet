package database.entity.dto;

import java.sql.Timestamp;
import java.util.Set;

import database.entity.IssueStatus;

public class JiraIssueDTO
{
	private int jiraIssueId;
	private JiraProjectDTO jiraProject;
	private IssueReporterDTO issueReporter;
	private IssueTypeDTO issueType;
	private Set<IssueCommentDTO> issueComments;
	private IssueResolutionDTO issueResolution;
	private IssueStatus issueStatus;
	private IssuePriorityDTO issuePriority;
	private String summary;
	private String code;
	private Timestamp createdAt = null;
	private Timestamp firstResponseDate = null;
	private String description;
	private String purpose = "T";
	private Set<AssignedIssueDTO> assignedIssues;

	public int getJiraIssueId()
	{
		return jiraIssueId;
	}

	public void setJiraIssueId(int jiraIssueId)
	{
		this.jiraIssueId = jiraIssueId;
	}

	public JiraProjectDTO getJiraProject()
	{
		return jiraProject;
	}

	public void setJiraProject(JiraProjectDTO jiraProject)
	{
		this.jiraProject = jiraProject;
	}

	public IssueReporterDTO getIssueReporter()
	{
		return issueReporter;
	}

	public void setIssueReporter(IssueReporterDTO issueReporter)
	{
		this.issueReporter = issueReporter;
	}

	public IssueTypeDTO getIssueType()
	{
		return issueType;
	}

	public void setIssueType(IssueTypeDTO issueType)
	{
		this.issueType = issueType;
	}

	public Set<IssueCommentDTO> getIssueComments()
	{
		return issueComments;
	}

	public void setIssueComments(Set<IssueCommentDTO> issueComments)
	{
		this.issueComments = issueComments;
	}

	public IssueResolutionDTO getIssueResolution()
	{
		return issueResolution;
	}

	public void setIssueResolution(IssueResolutionDTO issueResolution)
	{
		this.issueResolution = issueResolution;
	}

	public IssueStatus getIssueStatus()
	{
		return issueStatus;
	}

	public void setIssueStatus(IssueStatus issueStatus)
	{
		this.issueStatus = issueStatus;
	}

	public IssuePriorityDTO getIssuePriority()
	{
		return issuePriority;
	}

	public void setIssuePriority(IssuePriorityDTO issuePriority)
	{
		this.issuePriority = issuePriority;
	}

	public String getSummary()
	{
		return summary;
	}

	public void setSummary(String summary)
	{
		this.summary = summary;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public Timestamp getCreatedAt()
	{
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt)
	{
		this.createdAt = (Timestamp) createdAt.clone();
	}

	public Timestamp getFirstResponseDate()
	{
		return firstResponseDate;
	}

	public void setFirstResponseDate(Timestamp firstResponseDate)
	{
		this.firstResponseDate = (Timestamp) firstResponseDate.clone();
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getPurpose()
	{
		return purpose;
	}

	public void setPurpose(String purpose)
	{
		this.purpose = purpose;
	}

	public Set<AssignedIssueDTO> getAssignedIssues()
	{
		return assignedIssues;
	}

	public void setAssignedIssues(Set<AssignedIssueDTO> assignedIssues)
	{
		this.assignedIssues = assignedIssues;
	}

}
