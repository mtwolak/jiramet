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

	public Timestamp getFirstResponseDate(Issue issue)
	{
		if (issue == null)
		{
			return null;
		}
		if (issue.getFieldByName("First Response Date") != null
				&& issue.getFieldByName("First Response Date").getValue() != null)
		{
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSX");
			Timestamp firstResponseDate = DateConverter.convertStringToTimestamp(
					issue.getFieldByName("First Response Date").getValue().toString(), format);
			return firstResponseDate;
		}
		if (issue.getFieldByName("Date of 1st Reply") != null
				&& issue.getFieldByName("Date of 1st Reply").getValue() != null)
		{
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSX");
			Timestamp firstResponseDate = DateConverter
					.convertStringToTimestamp(issue.getFieldByName("Date of 1st Reply").getValue().toString(), format);
			return firstResponseDate;
		}
		return null;
	}

	public Timestamp getFirstResolveDate(Issue issue)
	{
		if (issue == null)
		{
			return null;
		}
		if (issue.getFieldByName("Resolved") != null && issue.getFieldByName("Resolved").getValue() != null)
		{
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
			Timestamp resolved = DateConverter
					.convertStringToTimestamp(issue.getFieldByName("Resolved").getValue().toString(), format);
			return resolved;
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
