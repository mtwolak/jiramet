package jira.data;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.atlassian.jira.rest.client.api.domain.BasicPriority;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.IssueType;
import com.atlassian.jira.rest.client.api.domain.Resolution;
import com.atlassian.jira.rest.client.api.domain.User;

import database.entity.IssueStatus;
import utils.DateConverter;

public class FieldPicker
{
	private DateFormat defaultFormat;
	private String firstResponse;
	private String firstReply;

	public FieldPicker()
	{
		defaultFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
		firstResponse = "First Response Date";
		firstReply = "Date of 1st Reply";
	}

	public Timestamp getFirstResponseDate(Issue issue)
	{
		if (issue == null)
		{
			return null;
		}
		if (issue.getFieldByName(firstResponse) != null && issue.getFieldByName(firstResponse).getValue() != null)
		{
			return convertToTimeStamp(issue.getFieldByName(firstResponse).getValue().toString());
		}
		if (issue.getFieldByName(firstReply) != null && issue.getFieldByName(firstReply).getValue() != null)
		{
			return convertToTimeStamp(issue.getFieldByName(firstReply).getValue().toString());
		}
		return null;
	}

	private Timestamp convertToTimeStamp(String date)
	{
		Timestamp timestamp = DateConverter.convertStringToTimestamp(date, defaultFormat);
		return timestamp;
	}

	public Timestamp getFirstResolveDate(Issue issue)
	{
		if (issue == null)
		{
			return null;
		}
		if (issue.getFieldByName("Resolved") != null && issue.getFieldByName("Resolved").getValue() != null)
		{
			return convertToTimeStamp(issue.getFieldByName("Resolved").getValue().toString());
		}
		return null;
	}

	public String getAssignee(User agne)
	{
		if (agne == null)
		{
			return "Unassigned";
		}
		return agne.getDisplayName();
	}

	public String getReporter(User reporter)
	{
		if (reporter == null)
		{
			return "NotIdentified";
		}
		return reporter.getDisplayName();
	}

	public String getResolution(Resolution resolution)
	{
		if (resolution == null)
		{
			return "NotSelected";
		}
		return resolution.getName();
	}

	public String getType(IssueType type)
	{
		if (type == null)
		{
			return "NotSelected";
		}
		return type.getName();
	}

	public String getPriority(BasicPriority priority)
	{
		if (priority == null)
		{
			return "NotSelected";
		}
		return priority.getName();
	}

	public IssueStatus getStatus(String status)
	{
		if (status == null)
		{
			return null;
		}
		if ("Resolved".equals(status))
		{
			return IssueStatus.RESOLVED;
		}
		return IssueStatus.CLOSED;
	}

}
