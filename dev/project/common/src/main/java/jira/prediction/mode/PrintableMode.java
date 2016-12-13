package jira.prediction.mode;

import utils.properties.PropertiesReader;

public abstract class PrintableMode
{

	public abstract void print(String textToPrint, PropertiesReader propertiesReader);

	public void init(PropertiesReader propertiesReader)
	{
	}

}
