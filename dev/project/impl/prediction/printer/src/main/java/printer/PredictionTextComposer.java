package printer;

import database.entity.JiraIssue;
import jira.AssigneeTimeResolve;
import jira.prediction.PredictionPrintable;
import utils.properties.PropertiesReader;

public class PredictionTextComposer extends PredictionPrintable
{
	public PredictionTextComposer(PropertiesReader propertiesReader)
	{
		super(propertiesReader);
	}

	private static final String NEW_LINE = "\n";
	private static final int DEFAULT_CAPACITY_STRINGBUILDER_PER_ASSIGNEE = 200;

	@Override
	protected String getPrediction(JiraIssue newIssue, AssigneeTimeResolve assigneeIssueTimes)
	{
		return new String(getInformationAboutAssigneeAndTheirTime(assigneeIssueTimes));
	}

	private String getInformationAboutAssigneeAndTheirTime(AssigneeTimeResolve assigneeIssueTime)
	{
		StringBuilder sb = new StringBuilder(DEFAULT_CAPACITY_STRINGBUILDER_PER_ASSIGNEE);
		sb.append("Assignee: ").append(assigneeIssueTime.getAssignee()).append(", time: ").append(assigneeIssueTime.getPredictedTime())
				.append(NEW_LINE);
		return sb.toString();
	}

}
