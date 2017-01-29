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
	 * Constructs prediction printable object with properties given by
	 * properties reader
	 * 
	 * @param propertiesReader
	 *            properties reader
	 */
	public PredictionPrintable(PropertiesReader propertiesReader)
	{
		this.propertiesReader = propertiesReader;
		this.predictionPrintMode = PredictionPrintMode
				.fromString(propertiesReader.getAsString(Property.PREDICTION_PRINTING));
		this.predictionPrintMode.init(propertiesReader);
	}

	protected abstract String getPrediction(AssigneeTimeResolve assigneeIssueTimes, double meanSquaredError);

	protected abstract String getPrediction(AssigneeTimeResolve assigneeIssueTimes);

	/**
	 * Printing prediction
	 * @param assigneeTimeResolve assignee with prediction time
	 * @param meanSquaredError mean squared error
	 */
	public void printPrediction(AssigneeTimeResolve assigneeTimeResolve, double meanSquaredError)
	{
		predictionPrintMode.print(getPrediction(assigneeTimeResolve, meanSquaredError), propertiesReader);
	}

	/**
	 * Printing prediction
	 * @param assigneeTimeResolve assignee with prediction time
	 */
	public void printPrediction(AssigneeTimeResolve assigneeTimeResolve)
	{
		predictionPrintMode.print(getPrediction(assigneeTimeResolve), propertiesReader);
	}

	/**
	 * Printing text
	 * @param text text to print
	 */
	public void print(String text)
	{
		predictionPrintMode.print(text, propertiesReader);
	}

	/**
	 * Printing text followed by new line
	 * @param text text to print followed by new line
	 */
	public void println(String text)
	{
		predictionPrintMode.print(text, propertiesReader);
		printNewLine();
	}

	/**
	 * Printing new line
	 */
	public void printNewLine()
	{
		predictionPrintMode.printNewLine(propertiesReader);
	}

}
