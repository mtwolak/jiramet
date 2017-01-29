package utils.time;

import java.sql.Timestamp;

import database.entity.AssignedIssue;
import utils.converter.TimestampConverter;

/**
 * Class for calculating time between issue creation and its resolve
 *
 */
public class ResolveTimeCalculator
{
	/**
	 * Calculating resolve time of given issue
	 * 
	 * @param assignedIssue
	 *            jira issue
	 * @return resolve time in days
	 */
	public static double getResolveTime(AssignedIssue assignedIssue)
	{
		Timestamp start = assignedIssue.getJiraIssue().getCreatedAt();
		Timestamp finish = assignedIssue.getResolvedAt();
		return TimestampConverter.getDifferenceInDays(finish, start);
	}
}
