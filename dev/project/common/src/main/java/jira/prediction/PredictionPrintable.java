package jira.prediction;

import database.entity.JiraIssue;
import jira.AssigneeTimeResolve;
import utils.properties.PropertiesReader;
import utils.properties.Property;

public abstract class PredictionPrintable
{
	protected PropertiesReader propertiesReader;

	public PredictionPrintable(PropertiesReader propertiesReader)
	{
		this.propertiesReader = propertiesReader;
	}

	protected abstract String getPrediction(JiraIssue newIssue, AssigneeTimeResolve assigneeIssueTimes);

	public void printPrediction(JiraIssue newIssue, AssigneeTimeResolve assigneeIssueTimes)
	{
		PredictionPrintMode predictionPrintMode = PredictionPrintMode
				.fromString(propertiesReader.getAsString(Property.PREDICTION_PRINTING));
		predictionPrintMode.print(getPrediction(newIssue, assigneeIssueTimes), propertiesReader);
	}

}
