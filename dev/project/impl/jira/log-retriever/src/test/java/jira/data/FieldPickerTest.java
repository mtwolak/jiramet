package jira.data;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.sql.Timestamp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.util.concurrent.Promise;

import database.entity.IssueStatus;
import jira.connector.JiraUtil;

public class FieldPickerTest
{
	private JiraUtil ju;
	private FieldPicker fieldPicker;
	private Issue testIssueSrp;
	private Issue testIssueMongo;
	private Issue testIssueCam;

	@Before
	public void setUp()
	{
		ju = new JiraUtil();
		fieldPicker = new FieldPicker();
		setTestIssues();
	}

	@After
	public void close()
	{
		ju.closeClientConnection();
	}
	
	@Test
	public void testGetFirstResponseDateNull()
	{
		assertNull(fieldPicker.getFirstResponseDate(null));
	}
	
	@Test
	public void testGetFirstResponseDateCase1() // contains field "First Response Date"
	{
		assertTrue(fieldPicker.getFirstResponseDate(testIssueSrp) == null || fieldPicker.getFirstResponseDate(testIssueSrp) instanceof Timestamp);
	}
	
	@Test
	public void testGetFirstResponseDateCase2() // no first response date field
	{
		assertTrue(fieldPicker.getFirstResponseDate(testIssueMongo) == null || fieldPicker.getFirstResponseDate(testIssueMongo) instanceof Timestamp);
	}
	
	@Test
	public void testGetFirstResponseDateCase3() // contains field "Date of 1st Reply"
	{
		assertTrue(fieldPicker.getFirstResponseDate(testIssueCam) == null || fieldPicker.getFirstResponseDate(testIssueCam) instanceof Timestamp);
	}

	@Test
	public void testGetFirstResolveDateNull()
	{
		assertNull(fieldPicker.getFirstResolveDate(null));
	}

	@Test
	public void testGetFirstResolveDate()
	{
		assertThat(fieldPicker.getFirstResolveDate(testIssueSrp), instanceOf(Timestamp.class));
	}

	@Test
	public void testGetAssignee()
	{
		assertThat(fieldPicker.getAssignee(testIssueSrp.getAssignee()), instanceOf(String.class));
	}

	@Test
	public void testGetAssigneeNull()
	{
		assertEquals("Unassigned", fieldPicker.getAssignee(null));
	}

	@Test
	public void testGetReporter()
	{
		assertThat(fieldPicker.getReporter(testIssueSrp.getReporter()), instanceOf(String.class));
	}

	@Test
	public void testGetReporterNull()
	{
		assertEquals("NotIdentified", fieldPicker.getReporter(null));
	}

	@Test
	public void testGetResolution()
	{
		assertThat(fieldPicker.getResolution(testIssueSrp.getResolution()), instanceOf(String.class));
	}

	@Test
	public void testGetResolutionNull()
	{
		assertEquals("NotSelected", fieldPicker.getResolution(null));
	}

	@Test
	public void testGetType()
	{
		assertThat(fieldPicker.getType(testIssueSrp.getIssueType()), instanceOf(String.class));
	}

	@Test
	public void testGetTypeNull()
	{
		assertEquals("NotSelected", fieldPicker.getType(null));
	}

	@Test
	public void testGetPriority()
	{
		assertThat(fieldPicker.getPriority(testIssueSrp.getPriority()), instanceOf(String.class));
	}

	@Test
	public void testGetPriorityNull()
	{
		assertEquals("NotSelected", fieldPicker.getPriority(null));
	}

	@Test
	public void testGetStatus()
	{
		assertThat(fieldPicker.getStatus(testIssueSrp.getStatus().getDescription()), instanceOf(IssueStatus.class));
	}

	@Test
	public void testGetStatusNull()
	{
		assertNull(fieldPicker.getStatus(null));
	}
	
	@Test
	public void testGetStatusResolved()
	{
		assertEquals(IssueStatus.RESOLVED, fieldPicker.getStatus("Resolved"));
	}

	@Test
	public void testGetStatusDefault()
	{
		assertEquals(IssueStatus.CLOSED, fieldPicker.getStatus("any"));
	}

	private void setTestIssues()
	{
		Promise<SearchResult> resultSpr = ju.getIssuesFromProject(ProjectData.SPRING, 0,1);
		testIssueSrp = resultSpr.claim().getIssues().iterator().next();
		
		Promise<SearchResult> resultMongo = ju.getIssuesFromProject(ProjectData.MONGODB, 0,1);
		testIssueMongo = resultMongo.claim().getIssues().iterator().next();
		
		Promise<SearchResult> resultCam = ju.getIssuesFromProject(ProjectData.CAMUNDA, 0,1);
		testIssueCam = resultCam.claim().getIssues().iterator().next();
	}

}
