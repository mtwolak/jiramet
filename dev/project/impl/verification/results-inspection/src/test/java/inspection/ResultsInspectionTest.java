package inspection;

import java.util.ArrayList;
import java.util.List;

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
import test.util.TimeStampConverter;

@RunWith(MockitoJUnitRunner.class)
public class ResultsInspectionTest
{
	@Mock
	private JiraIssueWithPredictedTimeToResolve predictedTimeResolveWithJiraMock;
	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	private AssignedIssue assignedIssueMock;
	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	private AssignedIssue assignedIssueMock2;
	@Mock
	private AssigneeTimeResolve assigneeTimeResolveMock;
	@Mock
	private AssigneeTimeResolve assigneeTimeResolveMock2;

	private ResultsInspection resultsInspection;

	@Before
	public void init()
	{
		resultsInspection = new ResultsInspection();
	}

	@Test
	public void shouldEvaluateMeanSquaredError()
	{
		new TimeTestJira().setRealTimeResolveJiraIssue(8, assignedIssueMock).setPredictedTime(6.25, assigneeTimeResolveMock);
		preparePredicterTimeResolveWithJiraIssue();

		double meanSquaredError = resultsInspection.getMeanSquaredError(predictedTimeResolveWithJiraMock);

		Assert.assertThat(meanSquaredError, Matchers.is(1.75));

	}

	@Test
	public void shouldEvaluateRootMeanSquaredError()
	{
		new TimeTestJira().setRealTimeResolveJiraIssue(2.5, assignedIssueMock).setPredictedTime(4, assigneeTimeResolveMock);
		new TimeTestJira().setRealTimeResolveJiraIssue(12.5, assignedIssueMock2).setPredictedTime(10, assigneeTimeResolveMock2);

		double rootMeanSquaredError = resultsInspection.getRootMeanSquaredError(createListJiraWithIssuePredictedTimeToResolve());

		Assert.assertEquals(2.061552, rootMeanSquaredError, 0.000001);
	}
	
	@Test
	public void shouldEvaluateCoefficientOfDetermination()
	{
		new TimeTestJira().setRealTimeResolveJiraIssue(2.5, assignedIssueMock).setPredictedTime(4, assigneeTimeResolveMock);
		new TimeTestJira().setRealTimeResolveJiraIssue(12.5, assignedIssueMock2).setPredictedTime(10, assigneeTimeResolveMock2);
		
		double coefficientOfDetermination = resultsInspection.getCoefficientOfDetermination(createListJiraWithIssuePredictedTimeToResolve());
		
		double mean = (2.5 + 12.5) / 2;
		double up = Math.pow((4 - mean), 2) + Math.pow((10-mean), 2);
		double down = Math.pow((2.5-mean), 2) + Math.pow((12.5-mean), 2);
		
		Assert.assertEquals(up / down, coefficientOfDetermination, 0.0001);
	}

	private List<JiraIssueWithPredictedTimeToResolve> createListJiraWithIssuePredictedTimeToResolve()
	{
		List<JiraIssueWithPredictedTimeToResolve> list = new ArrayList<>();
		JiraIssueWithPredictedTimeToResolve l1 = new JiraIssueWithPredictedTimeToResolve(assignedIssueMock, assigneeTimeResolveMock);
		JiraIssueWithPredictedTimeToResolve l2 = new JiraIssueWithPredictedTimeToResolve(assignedIssueMock2, assigneeTimeResolveMock2);
		list.add(l1);
		list.add(l2);
		return list;
	}

	private void preparePredicterTimeResolveWithJiraIssue()
	{
		Mockito.when(predictedTimeResolveWithJiraMock.getJiraIssue()).thenReturn(assignedIssueMock);
		Mockito.when(predictedTimeResolveWithJiraMock.getAssigneeTimeResolve()).thenReturn(assigneeTimeResolveMock);
	}

}

class TimeTestJira
{
	
	private TimeStampConverter createTimeStampConverter()
	{
		TimeStampConverter converter = new TimeStampConverter();
		converter.setDate(2016, 20, 10, 4, 4);
		return converter;
	}

	public TimeTestJira setPredictedTime(double timeResolveInDays, AssigneeTimeResolve assigneeTimeResolveMock)
	{
		Mockito.when(assigneeTimeResolveMock.getPredictedTime()).thenReturn(timeResolveInDays);
		return this;
	}

	public TimeTestJira setRealTimeResolveJiraIssue(double resolveTime, AssignedIssue assignedIssueMock)
	{
		TimeStampConverter converter = createTimeStampConverter();
		Mockito.when(assignedIssueMock.getJiraIssue().getCreatedAt()).thenReturn(converter.getTimeStamp());
		converter.addDays(resolveTime);
		Mockito.when(assignedIssueMock.getResolvedAt()).thenReturn(converter.getTimeStamp());
		return this;
	}
	
}