package utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class DateConverter
{
	public static Timestamp convertStringToTimestamp(String dateString, DateFormat dateFormat)
	{
		if (dateString == null || dateFormat == null)
		{
			return null;
		}
		Date date;
		try
		{
			date = dateFormat.parse(dateString);
		} catch (ParseException e)
		{
			return null;
		}
		return new Timestamp(date.getTime());
	}
}
