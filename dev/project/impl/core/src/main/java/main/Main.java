package main;

import database.exception.IssueNotFoundException;
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
			PredictionModelViewer predictionModelViewer = new PredictionModelViewer(propertiesReader);
			predictionModelViewer.init();
			predictionModelViewer.showPrediction();
		} catch (InsufficientArgumentsException e)
		{
			application.showUsage();
		} catch (IssueNotFoundException e)
		{
			application.showIssueNotFoundText(e.getMessage());
		}

	}

}
