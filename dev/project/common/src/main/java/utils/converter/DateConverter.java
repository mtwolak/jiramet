package utils.converter;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * Class for evaluating date conversion
 * 
 *
 */
public class DateConverter
{
	/**
	 * Converts date given in string accoring to given date format
	 * @param dateString date as string
	 * @param dateFormat date format
	 * @return Converted date
	 */
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
