package jira.prediction;

import jira.prediction.mode.ConsolePrintableMode;
import jira.prediction.mode.FilePrintableMode;
import jira.prediction.mode.PrintableMode;
import utils.properties.PropertiesReader;

public enum PredictionPrintMode
{
	CONSOLE(new ConsolePrintableMode()), FILE(new FilePrintableMode());
	private static final String NEW_LINE = System.getProperty("line.separator");

	private PrintableMode printableMode;

	private PredictionPrintMode(PrintableMode printableMode)
	{
		this.printableMode = printableMode;
	}

	public static PredictionPrintMode fromString(String predictionMode)
	{
		for (PredictionPrintMode mode : values())
		{
			if (mode.toString().equalsIgnoreCase(predictionMode))
			{
				return mode;
			}
		}
		throw new IncorrectPredictionModeException(predictionMode);
	}

	public void print(String textToPrint, PropertiesReader propertiesReader)
	{
		printableMode.print(textToPrint, propertiesReader);
	}

	public void init(PropertiesReader propertiesReader)
	{
		this.printableMode.init(propertiesReader);
	}

	public void printNewLine(PropertiesReader propertiesReader)
	{
		printableMode.print(NEW_LINE, propertiesReader);
	}
}
