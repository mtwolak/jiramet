package jira;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import database.entity.Assignee;
import database.entity.JiraIssue;

public class AssigneeIssueSimilarityTest {

	private AssigneeIssueSimilarity testObject;
	private Assignee assignee;
	private JiraIssueSimilarity jiraIssueSimilarity;
	
	@Before
	public void setUp()
	{
		testObject = new AssigneeIssueSimilarity(assignee, new ArrayList<JiraIssueSimilarity>());
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
		assertTrue(testObject.getAssignedJiraIssues().isEmpty());
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
		ArrayList<JiraIssueSimilarity> testList = new ArrayList<>();
		testList.add(jiraIssueSimilarity);
		testObject.setAssigneedIssues(testList);
		assertEquals(testObject.getAssignedJiraIssues().size(), 1);
	}
	
}

