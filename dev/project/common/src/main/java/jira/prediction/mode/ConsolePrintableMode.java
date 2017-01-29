package jira.prediction.mode;

import utils.properties.PropertiesReader;

/**
 * Implementation of printing to console
 */
public class ConsolePrintableMode extends PrintableMode
{
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void print(String textToPrint, PropertiesReader propertiesReader)
	{
		System.out.println(textToPrint);
	}

}
