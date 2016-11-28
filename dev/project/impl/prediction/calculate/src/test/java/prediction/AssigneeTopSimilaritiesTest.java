package prediction;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import database.entity.Assignee;
import database.entity.JiraIssue;
import jira.JiraIssueSimilarity;

public class AssigneeTopSimilaritiesTest
{
	private AssigneeTopSimilarities testObject;
	private Assignee assignee;
	private JiraIssueSimilarity jiraIssueSimilarity;
	
	@Before
	public void setUp()
	{
		testObject = new AssigneeTopSimilarities(5);
		assignee = new Assignee();
		prepareTestObjects();
	}

	private void prepareTestObjects()
	{
		assignee.setName("Michal");
		JiraIssue temp = new JiraIssue();
		temp.setCode("TST-222");
		jiraIssueSimilarity = new JiraIssueSimilarity(temp, 0.22);		
	}

	@Test
	public void testGetAssigneeNull()
	{
		assertNull(testObject.getAssignee());
	}
	
	@Test
	public void testGetJiraIssueSimilarityEmpty()
	{
		assertTrue(testObject.getJiraIssueSimilarities().isEmpty());
	}

	@Test
	public void testSetAssignee()
	{
		testObject.setAssignee(assignee);
		assertNotNull(testObject.getAssignee());
	}
	
	@Test
	public void testAddJiraIssueSimilarity()
	{
		testObject.addJiraIssueSimilarity(jiraIssueSimilarity);
		assertEquals(testObject.getJiraIssueSimilarities().size(), 1);
	}
	
}
