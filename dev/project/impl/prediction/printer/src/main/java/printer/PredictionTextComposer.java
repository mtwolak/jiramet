package printer;

import jira.AssigneeTimeResolve;
import jira.prediction.PredictionPrintable;
import utils.converter.NumberConverter;
import utils.properties.PropertiesReader;

public class PredictionTextComposer extends PredictionPrintable
{
	private static final String PREDICTED_TIME = ", predicted time: ";
	private static final int DEFAULT_CAPACITY_STRINGBUILDER_PER_ASSIGNEE = 200;
	private static final String NOT_ENOUGH_DATA_TO_SHOW = "not enough data to predict";

	public PredictionTextComposer(PropertiesReader propertiesReader)
	{
		super(propertiesReader);
	}

	@Override
	protected String getPrediction(AssigneeTimeResolve assigneeIssueTimes, double meanSquareError)
	{
		return new String(getInformationAboutRealAssigneeAndHisTime(assigneeIssueTimes, meanSquareError));
	}

	@Override
	protected String getPrediction(AssigneeTimeResolve assigneeIssueTimes)
	{
		return new String(getInformationAboutAssigneeAndHisPredictedTime(assigneeIssueTimes));
	}

	private String getInformationAboutAssigneeAndHisPredictedTime(AssigneeTimeResolve assigneeIssueTime)
	{
		StringBuilder sb = new StringBuilder(DEFAULT_CAPACITY_STRINGBUILDER_PER_ASSIGNEE);
		sb.append("Assignee: ").append(assigneeIssueTime.getAssignee()).append(PREDICTED_TIME).append(getTimePrediction(assigneeIssueTime));
		return sb.toString();
	}

	private String getInformationAboutRealAssigneeAndHisTime(AssigneeTimeResolve assigneeIssueTime, double meanSquareError)
	{
		StringBuilder sb = new StringBuilder(DEFAULT_CAPACITY_STRINGBUILDER_PER_ASSIGNEE);
		sb.append("Real assignee: ").append(assigneeIssueTime.getAssignee()).append(PREDICTED_TIME)
				.append(getTimePrediction(assigneeIssueTime)).append(", mean squared error: " + NumberConverter.format(meanSquareError));
		return sb.toString();
	}

	private String getTimePrediction(AssigneeTimeResolve assigneeIssueTime)
	{
		double predictedTime = assigneeIssueTime.getPredictedTime();
		return assigneeIssueTime.getPredictedTime() == 0 ? NOT_ENOUGH_DATA_TO_SHOW : String.valueOf(NumberConverter.format(predictedTime));
	}

}
