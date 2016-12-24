package retriever.project;

import jira.project.ProjectData;
import utils.properties.PropertiesReader;
import utils.properties.Property;

public class ProjectRetriever
{

	private PropertiesReader propertiesReader;

	public ProjectRetriever(PropertiesReader propertiesReader)
	{
		this.propertiesReader = propertiesReader;
	}

	public ProjectData getProjectFromProperties()
	{
		String projectUrl = propertiesReader.getAsString(Property.PROJECT_URL);
		String projectKey = propertiesReader.getAsString(Property.PROJECT_KEY);
		String projectName = propertiesReader.getAsString(Property.PROJECT_NAME);
		int issueLimit = propertiesReader.getAsInt(Property.PROJECT_ISSUE_LIMIT);
		return new ProjectData(projectUrl, projectKey, projectName, issueLimit);
	}

}
