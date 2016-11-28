package jira.prediction;

import java.util.List;

import database.entity.JiraIssue;
import jira.AssigneeTimeResolve;
import utils.properties.PropertiesReader;
import utils.properties.Property;

public abstract class PredictionPrintable
{
	public abstract String getPrediction(JiraIssue newIssue, List<AssigneeTimeResolve> assigneeIssueTimes);

	public void printPrediction(JiraIssue newIssue, List<AssigneeTimeResolve> assigneeIssueTimes)
	{
		PredictionPrintMode predictionPrintMode = PredictionPrintMode
				.fromString(new PropertiesReader().get(Property.PREDICTION_PRINTING));
		predictionPrintMode.print(getPrediction(newIssue, assigneeIssueTimes));
	}

}
