package retriever.project;

import jira.project.ProjectData;
import utils.properties.PropertiesReader;
import utils.properties.Property;

public class ProjectRetriever
{

	private PropertiesReader propertiesReader;

	/**
	 * Creates a new instance of ProjectRetriever class and initialize all necessary variables
	 * 
	 * @param propertiesReader properties reader, grants access to system configuration variables
	 * @see PropertiesReader
	 */
	public ProjectRetriever(PropertiesReader propertiesReader)
	{
		this.propertiesReader = propertiesReader;
	}
	/**
	 * Returns information about the project defined in the system configuration file 
	 * 
	 * @return essential data about the particular JIRA project
	 * @see ProjectData
	 */
	public ProjectData getProjectFromProperties()
	{
		String projectUrl = propertiesReader.getAsString(Property.PROJECT_URL);
		String projectKey = propertiesReader.getAsString(Property.PROJECT_KEY);
		String projectName = propertiesReader.getAsString(Property.PROJECT_NAME);
		int issueLimit = propertiesReader.getAsInt(Property.PROJECT_ISSUE_LIMIT);
		return new ProjectData(projectUrl, projectKey, projectName, issueLimit);
	}

}
