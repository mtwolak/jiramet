package utils.converter;

import java.text.DecimalFormat;

public class NumberConverter
{
	private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

	public static String format(double number)
	{
		return DECIMAL_FORMAT.format(number);
	}
}
