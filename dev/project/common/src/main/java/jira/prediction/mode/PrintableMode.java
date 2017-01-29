package jira.prediction.mode;

import utils.properties.PropertiesReader;

/**
 * Class for printing data
 */
public abstract class PrintableMode
{
	/**
	 * Method for printing text
	 * @param textToPrint text to be printed
	 * @param propertiesReader property reader
	 */
	public abstract void print(String textToPrint, PropertiesReader propertiesReader);

	/**
	 * Method for executing init actions
	 * @param propertiesReader property reader
	 */
	public void init(PropertiesReader propertiesReader)
	{
	}

}
