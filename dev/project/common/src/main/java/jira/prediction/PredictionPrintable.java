package jira.prediction;

import database.entity.JiraIssue;
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

	protected abstract String getPrediction(JiraIssue newIssue, AssigneeTimeResolve assigneeIssueTimes);

	public void printPrediction(JiraIssue newIssue, AssigneeTimeResolve assigneeIssueTimes)
	{
		predictionPrintMode.print(getPrediction(newIssue, assigneeIssueTimes), propertiesReader);
	}

}
