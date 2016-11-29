package main;

import utils.InsufficientArgumentsException;
import utils.UsageApplication;
import utils.properties.PropertiesReader;

public class Main
{

	public static void main(String args[])
	{
		UsageApplication application = new UsageApplication(args);
		try
		{
			PropertiesReader propertiesReader = new PropertiesReader(application.getArg(1));
			new PredictionModelViewer(propertiesReader).show();
		} catch (InsufficientArgumentsException e)
		{
			application.showUsage();
		}

	}

}
