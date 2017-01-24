package jira.prediction;

import jira.AssigneeTimeResolve;
import utils.properties.PropertiesReader;
import utils.properties.Property;

public abstract class PredictionPrintable
{
	protected PropertiesReader propertiesReader;
	private PredictionPrintMode predictionPrintMode;

	public PredictionPrintable(PropertiesReader propertiesReader)
	{
		this.propertiesReader = propertiesReader;
		this.predictionPrintMode = PredictionPrintMode.fromString(propertiesReader.getAsString(Property.PREDICTION_PRINTING));
		this.predictionPrintMode.init(propertiesReader);
	}

	protected abstract String getPrediction(AssigneeTimeResolve assigneeIssueTimes, double meanSquaredError);

	public void printPrediction(AssigneeTimeResolve assigneeIssueTimes, double meanSquaredError)
	{
		predictionPrintMode.print(getPrediction(assigneeIssueTimes, meanSquaredError), propertiesReader);
	}

	public void print(String text)
	{
		predictionPrintMode.print(text, propertiesReader);
	}
	
	public void println(String text)
	{
		predictionPrintMode.print(text, propertiesReader);
		printNewLine();
	}
	
	public void printNewLine()
	{
		predictionPrintMode.printNewLine(propertiesReader);
	}
	
}
