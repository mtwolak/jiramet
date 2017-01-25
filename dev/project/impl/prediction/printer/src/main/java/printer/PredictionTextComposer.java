package printer;

import jira.AssigneeTimeResolve;
import jira.prediction.PredictionPrintable;
import utils.converter.NumberConverter;
import utils.properties.PropertiesReader;

public class PredictionTextComposer extends PredictionPrintable
{
	private static final String NEW_LINE = "\n";
	private static final int DEFAULT_CAPACITY_STRINGBUILDER_PER_ASSIGNEE = 200;
	private static final String NOT_ENOUGH_DATA_TO_SHOW = "not enough data to predict";

	public PredictionTextComposer(PropertiesReader propertiesReader)
	{
		super(propertiesReader);
	}

	@Override
	protected String getPrediction(AssigneeTimeResolve assigneeIssueTimes, double meanSquareError)
	{
		return new String(getInformationAboutAssigneeAndTheirTime(assigneeIssueTimes, meanSquareError));
	}

	private String getInformationAboutAssigneeAndTheirTime(AssigneeTimeResolve assigneeIssueTime, double meanSquareError)
	{
		StringBuilder sb = new StringBuilder(DEFAULT_CAPACITY_STRINGBUILDER_PER_ASSIGNEE);
		sb.append("Assignee: ").append(assigneeIssueTime.getAssignee()).append(", time: ").append(getTimePrediction(assigneeIssueTime))
				.append(", mean squared error: " + NumberConverter.format(meanSquareError)).append(NEW_LINE);
		return sb.toString();
	}

	private String getTimePrediction(AssigneeTimeResolve assigneeIssueTime)
	{
		double predictedTime = assigneeIssueTime.getPredictedTime();
		return assigneeIssueTime.getPredictedTime() == 0 ? NOT_ENOUGH_DATA_TO_SHOW : String.valueOf(NumberConverter.format(predictedTime));
	}

}
