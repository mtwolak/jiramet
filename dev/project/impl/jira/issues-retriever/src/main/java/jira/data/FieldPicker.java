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
import utils.converter.DateConverter;

public class FieldPicker
{
	private DateFormat defaultFormat;
	private String firstResponse;
	private String firstReply;

	/**
	 * Creates a new instance of FieldPicker class and initialize all necessary variables
	 */
	public FieldPicker()
	{
		defaultFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
		firstResponse = "First Response Date";
		firstReply = "Date of 1st Reply";
	}

	/**
	 * 
	 * @param issue selected issue, from which date should be extracted
	 * @return first response date for selected issue converted to Timestamp object or null if it is not defined
	 */
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

	/**
	 * 
	 * @param issue selected issue, from which date should be extracted
	 * @return resolve date for selected issue converted to Timestamp object or null if it is not defined
	 */
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

	/**
	 * 
	 * @param agne selected user data, from which name should be extracted
	 * @return user name of the issue assignee or "Unassigned" if it is not defined
	 */
	public String getAssignee(User agne)
	{
		if (agne == null)
		{
			return "Unassigned";
		}
		return agne.getDisplayName();
	}

	/**
	 * 
	 * @param reporter selected user data, from which name should be extracted
	 * @return user name of the issue reporter or "NotIdentified" if it is not defined
	 */
	public String getReporter(User reporter)
	{
		if (reporter == null)
		{
			return "NotIdentified";
		}
		return reporter.getDisplayName();
	}

	/**
	 * 
	 * @param resolution selected issue resolution object, from which name should be extracted
	 * @return full name of the issue resolution or "NotSelected" if it is not defined
	 */
	public String getResolution(Resolution resolution)
	{
		if (resolution == null)
		{
			return "NotSelected";
		}
		return resolution.getName();
	}

	/**
	 * 
	 * @param type selected issue type object, from which name should be extracted
	 * @return full name of the issue type or "NotSelected" if it is not defined
	 */
	public String getType(IssueType type)
	{
		if (type == null)
		{
			return "NotSelected";
		}
		return type.getName();
	}

	/**
	 * 
	 * @param priority selected issue priority object, from which name should be extracted
	 * @return full name of the issue priority or "NotSelected" if it is not defined
	 */
	public String getPriority(BasicPriority priority)
	{
		if (priority == null)
		{
			return "NotSelected";
		}
		return priority.getName();
	}
	/**
	 * 
	 * @param status status of the selected issue
	 * @return enum equivalent of the selected status
	 * @see IssueStatus
	 */
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
