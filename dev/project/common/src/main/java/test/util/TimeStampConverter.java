package test.util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 * Class for converting time stamp to java date
 *
 */
public class TimeStampConverter
{
	private Date date;
	private static final double ONE_NIGHT_AND_DAY_IN_MILIS = 1000 * 60 * 60 * 24;

	/**
	 * Setting date
	 * @param year year
	 * @param month month
	 * @param day day
	 * @param hour hour
	 * @param minute minute
	 */
	public void setDate(int year, int month, int day, int hour, int minute)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.HOUR, hour);
		calendar.set(Calendar.MINUTE, minute);
		this.date = new Date(calendar.getTimeInMillis());
	}

	/**
	 * Adds days to current set date
	 * @param days number of days to be added
	 */
	public void addDays(double days)
	{
		long milis = date.getTime();
		int daysInSecondMilis = (int) (days * ONE_NIGHT_AND_DAY_IN_MILIS);
		this.date = new Date(milis + daysInSecondMilis);
	}

	/**
	 * Gets calculated timestamp of current set date
	 * @return converter timestamp
	 */
	public Timestamp getTimeStamp()
	{
		return new Timestamp(date.getTime());
	}

}