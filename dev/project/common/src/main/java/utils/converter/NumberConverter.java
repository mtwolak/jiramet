package utils.converter;

import java.text.DecimalFormat;

/**
 * Class for displaying rounded numbers
 *
 */
public class NumberConverter
{
	private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

	/**
	 * Formats given number 
	 * @param number number to be formatted
	 * @return formatted number as string
	 */
	public static String format(double number)
	{
		return DECIMAL_FORMAT.format(number);
	}
}
