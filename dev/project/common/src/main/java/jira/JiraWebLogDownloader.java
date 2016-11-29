package jira;

import jira.project.ProjectData;

public interface JiraWebLogDownloader 
{
	public void retrieveAllIssues();
	public void retrieveIssuesFromProject(ProjectData project);
}
