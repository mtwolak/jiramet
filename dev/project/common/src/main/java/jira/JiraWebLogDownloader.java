package jira;

import org.apache.log4j.Logger;

import jira.project.ProjectData;
import utils.properties.PropertiesReader;
import utils.properties.Property;

public abstract class JiraWebLogDownloader
{
	private PropertiesReader propertiesReader;
	private static final Logger LOGGER = Logger.getLogger(JiraWebLogDownloader.class.getName());

	public JiraWebLogDownloader(PropertiesReader propertiesReader)
	{
		this.propertiesReader = propertiesReader;
	}

	protected abstract void retrieveAllIssues();

	protected abstract void retrieveIssuesFromProject(ProjectData project);

	protected PropertiesReader getPropertiesReader()
	{
		return propertiesReader;
	}

	public void retrieveAllIssuesWithRespectToPropertyFlag()
	{
		if (isSetToTrue(Property.SHOULD_DOWNLOAD_ALL_ISSUES))
		{
			retrieveAllIssues();
		}
	}

	public void retrieveIssuesFromProjectWithRespectToPropertyFlag(ProjectData project)
	{
		if (isSetToTrue(Property.SHOULD_DOWNLOAD_ALL_ISSUES))
		{
			retrieveIssuesFromProject(project);
		}
	}

	private boolean isSetToTrue(Property property)
	{
		if (propertiesReader.getAsBoolean(property))
		{
			return true;
		} else
		{
			LOGGER.debug("Issues not downloaded due to false property " + property);
			return false;
		}
	}

}
