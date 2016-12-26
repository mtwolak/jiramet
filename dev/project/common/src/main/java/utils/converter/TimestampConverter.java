package utils.converter;

import java.sql.Timestamp;

public final class TimestampConverter
{

	private static final double MILIS_IN_DAY_AND_NIGHT = 1000 * 60 * 60 * 24;

	private TimestampConverter()
	{

	}

	public static double getDifferenceInDays(Timestamp finish, Timestamp start)
	{
		return (finish.getTime() - start.getTime()) / MILIS_IN_DAY_AND_NIGHT;
	}

}
