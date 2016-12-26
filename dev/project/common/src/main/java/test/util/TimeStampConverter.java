package test.util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class TimeStampConverter
{
	private Date date;
	private static final double ONE_NIGHT_AND_DAY_IN_MILIS = 1000 * 60 * 60 * 24;

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

	public void addDays(double days)
	{
		long milis = date.getTime();
		int daysInSecondMilis = (int) (days * ONE_NIGHT_AND_DAY_IN_MILIS);
		this.date = new Date(milis + daysInSecondMilis);
	}

	public Timestamp getTimeStamp()
	{
		return new Timestamp(date.getTime());
	}

}