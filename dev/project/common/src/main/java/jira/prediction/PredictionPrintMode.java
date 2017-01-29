package jira.prediction;

import jira.prediction.mode.ConsolePrintableMode;
import jira.prediction.mode.FilePrintableMode;
import jira.prediction.mode.PrintableMode;
import utils.properties.PropertiesReader;

/**
 * Available printing modes
 */
public enum PredictionPrintMode
{
	CONSOLE(new ConsolePrintableMode()), FILE(new FilePrintableMode());
	private static final String NEW_LINE = System.getProperty("line.separator");

	private PrintableMode printableMode;

	private PredictionPrintMode(PrintableMode printableMode)
	{
		this.printableMode = printableMode;
	}

	/**
	 * Gets printing mode form string
	 * 
	 * @param predictionMode
	 *            prediction print mode as string
	 * @return PredictionPrintingMode
	 */
	public static PredictionPrintMode fromString(String predictionMode)
	{
		for (PredictionPrintMode mode : values())
		{
			if (mode.toString().equalsIgnoreCase(predictionMode))
			{
				return mode;
			}
		}
		throw new IncorrectPredictionPrintModeException(predictionMode);
	}

	/**
	 * Printing text
	 * @param textToPrint text to be printed
	 * @param propertiesReader property reader
	 */
	public void print(String textToPrint, PropertiesReader propertiesReader)
	{
		printableMode.print(textToPrint, propertiesReader);
	}

	/**
	 * Initialization of printing mode
	 * @param propertiesReader property reader
	 */
	public void init(PropertiesReader propertiesReader)
	{
		this.printableMode.init(propertiesReader);
	}

	/**
	 * Printing new line
	 * @param propertiesReader property reader
	 */
	public void printNewLine(PropertiesReader propertiesReader)
	{
		printableMode.print(NEW_LINE, propertiesReader);
	}
}
