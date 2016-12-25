package inspection;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import database.entity.AssignedIssue;
import jira.AssigneeTimeResolve;
import results.JiraIssueWithPredictedTimeToResolve;

@RunWith(MockitoJUnitRunner.class)
public class ResultsInspectionTest
{
	@Mock
	private JiraIssueWithPredictedTimeToResolve predictedTimeResolveWithJiraMock;
	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	private AssignedIssue assignedIssueMock;
	@Mock
	private AssigneeTimeResolve assigneeTimeResolveMock;
	private ResultsInspection resultsInspection;

	@Before
	public void init()
	{
		resultsInspection = new ResultsInspection();
	}

	@Test
	public void shouldEvaluateMeanSquaredError()
	{
		setRealTimeResolveJiraIssue(8);
		setPredictedTime(6.25);
		preparePredicterTimeResolveWithJiraIssue();

		double meanSquaredError = resultsInspection.getMeanSquaredError(predictedTimeResolveWithJiraMock);

		Assert.assertThat(meanSquaredError, Matchers.is(1.75));

	}

	private void setPredictedTime(double timeResolveInDays)
	{
		Mockito.when(assigneeTimeResolveMock.getPredictedTime()).thenReturn(timeResolveInDays);
	}

	private void setRealTimeResolveJiraIssue(double timeResolveInDays)
	{
		TimeStampConverter converter = createTimeStampConverter();
		Mockito.when(assignedIssueMock.getJiraIssue().getCreatedAt()).thenReturn(converter.getTimeStamp());
		converter.addDays(timeResolveInDays);
		Mockito.when(assignedIssueMock.getResolvedAt()).thenReturn(converter.getTimeStamp());
	}

	private TimeStampConverter createTimeStampConverter()
	{
		TimeStampConverter converter = new TimeStampConverter();
		converter.setDate(2016, 20, 10, 4, 4);
		return converter;
	}

	private void preparePredicterTimeResolveWithJiraIssue()
	{
		Mockito.when(predictedTimeResolveWithJiraMock.getJiraIssue()).thenReturn(assignedIssueMock);
		Mockito.when(predictedTimeResolveWithJiraMock.getAssigneeTimeResolve()).thenReturn(assigneeTimeResolveMock);

	}

}

class TimeStampConverter
{
	private Date date;
	private static final int ONE_NIGHT_AND_DAY_IN_MILIS = 1000 * 60 * 60 * 24;

	public void setDate(int year, int month, int day, int hour, int minute)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.HOUR, hour);
		calendar.set(Calendar.MINUTE, minute);
		this.date = new Date(calendar.getTimeInMillis());
	}

	public void addDays(double days)
	{
		long milis = date.getTime();
		int daysInSecondMilis = new Double(days).intValue() * ONE_NIGHT_AND_DAY_IN_MILIS;
		this.date = new Date(milis + daysInSecondMilis);
	}

	public Timestamp getTimeStamp()
	{
		return new Timestamp(date.getTime());
	}

}
