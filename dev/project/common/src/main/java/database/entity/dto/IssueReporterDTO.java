package database.entity.dto;

public class IssueReporterDTO
{
	private int issueReportedId;

	private String fullName;

	public int getIssueReportedId()
	{
		return issueReportedId;
	}

	public void setIssueReportedId(int issueReportedId)
	{
		this.issueReportedId = issueReportedId;
	}

	public String getFullName()
	{
		return fullName;
	}

	public void setFullName(String fullName)
	{
		this.fullName = fullName;
	}
}
