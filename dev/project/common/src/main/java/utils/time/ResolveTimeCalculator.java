package utils.time;

import java.sql.Timestamp;

import database.entity.AssignedIssue;
import utils.converter.TimestampConverter;

public class ResolveTimeCalculator
{
	public static double getResolveTime(AssignedIssue assignedIssue)
	{
		Timestamp start = assignedIssue.getJiraIssue().getCreatedAt();
		Timestamp finish = assignedIssue.getResolvedAt();
		return TimestampConverter.getDifferenceInDays(finish, start);
	}
}
