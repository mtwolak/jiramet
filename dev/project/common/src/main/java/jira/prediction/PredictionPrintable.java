package jira.prediction;

import jira.AssigneeTimeResolve;
import utils.properties.PropertiesReader;
import utils.properties.Property;

/**
 * 
 * Class for printing predictions
 */
public abstract class PredictionPrintable
{
	protected PropertiesReader propertiesReader;
	private PredictionPrintMode predictionPrintMode;

	/**
	 * Constructs prediction printable object with properties given by properties
	 * reader
	 * 
	 * @param propertiesReader
	 *            - properties reader
	 */
	public PredictionPrintable(PropertiesReader propertiesReader)
	{
		this.propertiesReader = propertiesReader;
		this.predictionPrintMode = PredictionPrintMode.fromString(propertiesReader.getAsString(Property.PREDICTION_PRINTING));
		this.predictionPrintMode.init(propertiesReader);
	}

	protected abstract String getPrediction(AssigneeTimeResolve assigneeIssueTimes, double meanSquaredError);

	protected abstract String getPrediction(AssigneeTimeResolve assigneeIssueTimes);

	public void printPrediction(AssigneeTimeResolve assigneeIssueTimes, double meanSquaredError)
	{
		predictionPrintMode.print(getPrediction(assigneeIssueTimes, meanSquaredError), propertiesReader);
	}

	public void printPrediction(AssigneeTimeResolve assigneeIssueTimes)
	{
		predictionPrintMode.print(getPrediction(assigneeIssueTimes), propertiesReader);
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
