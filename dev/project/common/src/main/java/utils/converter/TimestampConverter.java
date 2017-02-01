package utils.converter;

import java.sql.Timestamp;
/**
 * Class for evaluating difference between two timestamps
 */
public final class TimestampConverter
{

	private static final double MILIS_IN_DAY_AND_NIGHT = 1000 * 60 * 60 * 24;

	private TimestampConverter()
	{

	}

	/**
	 * Calculating difference between two timestamps
	 * @param finish timestamp which indicates some event has ended its activity
	 * @param start timestamp which indicates some event has started its activity
	 * @return difference as number of days between two timestamps
	 */
	public static double getDifferenceInDays(Timestamp finish, Timestamp start)
	{
		if(finish == null || start == null)
		{
			return -1;
		}
		return (finish.getTime() - start.getTime()) / MILIS_IN_DAY_AND_NIGHT;
	}

}
