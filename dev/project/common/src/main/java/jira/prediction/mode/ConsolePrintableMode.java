package jira.prediction.mode;

import utils.properties.PropertiesReader;

public class ConsolePrintableMode extends PrintableMode
{
	@Override
	public void print(String textToPrint, PropertiesReader propertiesReader)
	{
		System.out.println(textToPrint);
	}

}
