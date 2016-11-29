package jira.project;

import static org.junit.Assert.*;

import org.junit.Test;

import jira.project.ProjectData;

public class ProjectDataTest
{

	@Test
	public void testGetProjectURLSpring()
	{
		assertEquals(ProjectData.SPRING.getProjectURL(), "https://jira.spring.io");
	}
	
	@Test
	public void testGetProjectURLMongo()
	{
		assertEquals(ProjectData.MONGODB.getProjectURL(), "https://jira.mongodb.org");
	}

	
	@Test
	public void testGetProjectURLCamunda()
	{
		assertEquals(ProjectData.CAMUNDA.getProjectURL(), "https://app.camunda.com/jira");
	}

	@Test
	public void testGetProjectKeySpring()
	{
		assertEquals(ProjectData.SPRING.getProjectKey(), "SPR");
	}
	
	@Test
	public void testGetProjectKeyMongo()
	{
		assertEquals(ProjectData.MONGODB.getProjectKey(), "SERVER");
	}

	
	@Test
	public void testGetProjectKeyCamunda()
	{
		assertEquals(ProjectData.CAMUNDA.getProjectKey(), "CAM");
	}
	
	@Test
	public void testGetProjectNameSpring()
	{
		assertEquals(ProjectData.SPRING.getProjectName(), "Spring Framework");
	}
	
	@Test
	public void testGetProjectNameMongo()
	{
		assertEquals(ProjectData.MONGODB.getProjectName(), "MongoDB Server");
	}

	
	@Test
	public void testGetProjectNameCamunda()
	{
		assertEquals(ProjectData.CAMUNDA.getProjectName(), "Camunda BPM");
	}
	
	@Test
	public void testGetIssueLimitPerCallSpring()
	{
		assertEquals(ProjectData.SPRING.getIssueLimitPerCall(), 1000);
	}
	
	@Test
	public void testGetIssueLimitPerCallMongo()
	{
		assertEquals(ProjectData.MONGODB.getIssueLimitPerCall(), 500);
	}

	
	@Test
	public void testGetIssueLimitPerCallCamunda()
	{
		assertEquals(ProjectData.CAMUNDA.getIssueLimitPerCall(), 800);
	}


}
