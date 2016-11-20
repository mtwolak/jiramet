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
	private Issue testIssueSrp;
	private Issue testIssueMongo;
	private Issue testIssueCam;

	@Before
	public void setUp()
	{
		ju = new JiraUtil();
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
		assertNull(FieldPicker.getFirstResponseDate(null));
	}
	
	@Test
	public void testGetFirstResponseDateCase1() // contains field "First Response Date"
	{
		assertTrue(FieldPicker.getFirstResponseDate(testIssueSrp) == null || FieldPicker.getFirstResponseDate(testIssueSrp) instanceof Timestamp);
	}
	
	@Test
	public void testGetFirstResponseDateCase2() // no first response date field
	{
		assertTrue(FieldPicker.getFirstResponseDate(testIssueMongo) == null || FieldPicker.getFirstResponseDate(testIssueMongo) instanceof Timestamp);
	}
	
	@Test
	public void testGetFirstResponseDateCase3() // contains field "Date of 1st Reply"
	{
		assertTrue(FieldPicker.getFirstResponseDate(testIssueCam) == null || FieldPicker.getFirstResponseDate(testIssueCam) instanceof Timestamp);
	}

	@Test
	public void testGetFirstResolveDateNull()
	{
		assertNull(FieldPicker.getFirstResolveDate(null));
	}

	@Test
	public void testGetFirstResolveDate()
	{
		assertThat(FieldPicker.getFirstResolveDate(testIssueSrp), instanceOf(Timestamp.class));
	}

	@Test
	public void testGetAssignee()
	{
		assertThat(FieldPicker.getAssignee(testIssueSrp.getAssignee()), instanceOf(String.class));
	}

	@Test
	public void testGetAssigneeNull()
	{
		assertEquals("Unassigned", FieldPicker.getAssignee(null));
	}

	@Test
	public void testGetReporter()
	{
		assertThat(FieldPicker.getReporter(testIssueSrp.getReporter()), instanceOf(String.class));
	}

	@Test
	public void testGetReporterNull()
	{
		assertEquals("NotIdentified", FieldPicker.getReporter(null));
	}

	@Test
	public void testGetResolution()
	{
		assertThat(FieldPicker.getResolution(testIssueSrp.getResolution()), instanceOf(String.class));
	}

	@Test
	public void testGetResolutionNull()
	{
		assertEquals("NotSelected", FieldPicker.getResolution(null));
	}

	@Test
	public void testGetType()
	{
		assertThat(FieldPicker.getType(testIssueSrp.getIssueType()), instanceOf(String.class));
	}

	@Test
	public void testGetTypeNull()
	{
		assertEquals("NotSelected", FieldPicker.getType(null));
	}

	@Test
	public void testGetPriority()
	{
		assertThat(FieldPicker.getPriority(testIssueSrp.getPriority()), instanceOf(String.class));
	}

	@Test
	public void testGetPriorityNull()
	{
		assertEquals("NotSelected", FieldPicker.getPriority(null));
	}

	@Test
	public void testGetStatus()
	{
		assertThat(FieldPicker.getStatus(testIssueSrp.getStatus().getDescription()), instanceOf(IssueStatus.class));
	}

	@Test
	public void testGetStatusNull()
	{
		assertNull(FieldPicker.getStatus(null));
	}
	
	@Test
	public void testGetStatusResolved()
	{
		assertEquals(IssueStatus.RESOLVED, FieldPicker.getStatus("Resolved"));
	}

	@Test
	public void testGetStatusDefault()
	{
		assertEquals(IssueStatus.CLOSED, FieldPicker.getStatus("any"));
	}

	private void setTestIssues()
	{
		Promise<SearchResult> resultSpr = ju.getIssuesFromSpringProject(0, 1);
		testIssueSrp = resultSpr.claim().getIssues().iterator().next();
		
		Promise<SearchResult> resultMongo = ju.getIssuesFromMongoDBProject(0, 1);
		testIssueMongo = resultMongo.claim().getIssues().iterator().next();
		
		Promise<SearchResult> resultCam = ju.getIssuesFromCamundaProject(0, 1);
		testIssueCam = resultCam.claim().getIssues().iterator().next();
	}

}
