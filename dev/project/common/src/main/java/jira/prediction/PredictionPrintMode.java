package jira.prediction;

import jira.prediction.mode.ConsolePrintableMode;
import jira.prediction.mode.FilePrintableMode;
import jira.prediction.mode.PrintableMode;
import utils.properties.PropertiesReader;

public enum PredictionPrintMode
{
	CONSOLE(new ConsolePrintableMode()), FILE(new FilePrintableMode());

	private PrintableMode printableMode;

	private PredictionPrintMode(PrintableMode printableMode)
	{
		this.printableMode = printableMode;
	}

	public static PredictionPrintMode fromString(String preditionMode)
	{
		for (PredictionPrintMode mode : values())
		{
			if (mode.toString().equalsIgnoreCase(preditionMode))
			{
				return mode;
			}
		}
		throw new IncorrectPredictionModeException(preditionMode);
	}

	public void print(String textToPrint, PropertiesReader propertiesReader)
	{
		printableMode.print(textToPrint, propertiesReader);
	}
}
