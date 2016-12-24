package retriever.project;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import jira.project.ProjectData;
import utils.properties.PropertiesReader;
import utils.properties.Property;

@RunWith(MockitoJUnitRunner.class)
public class ProjectRetrieverTest
{

	private static final int PROJECT_ISSUE_LIMIT = 200;
	private static final String PROJECT_URL = "Project_URL";
	private static final String PROJECT_KEY = "Project key";
	private static final String PROJECT_NAME = "Project name";
	
	
	@Mock
	private PropertiesReader propertiesReaderMock;

	@Test
	public void shouldGetProject()
	{
		preparePropertiesMock();
		ProjectRetriever projectRetriever = new ProjectRetriever(propertiesReaderMock);
		ProjectData projectFromProperties = projectRetriever.getProjectFromProperties();
		
		Assert.assertThat(projectFromProperties.getProjectKey(), Matchers.is(PROJECT_KEY));
		Assert.assertThat(projectFromProperties.getProjectName(), Matchers.is(PROJECT_NAME));
		Assert.assertThat(projectFromProperties.getProjectURL(), Matchers.is(PROJECT_URL));
		Assert.assertThat(projectFromProperties.getIssueLimitPerCall(), Matchers.is(PROJECT_ISSUE_LIMIT));
		
	}

	private void preparePropertiesMock()
	{
		Mockito.when(propertiesReaderMock.getAsInt(Property.PROJECT_ISSUE_LIMIT)).thenReturn(PROJECT_ISSUE_LIMIT);
		Mockito.when(propertiesReaderMock.getAsString(Property.PROJECT_URL)).thenReturn(PROJECT_URL);
		Mockito.when(propertiesReaderMock.getAsString(Property.PROJECT_KEY)).thenReturn(PROJECT_KEY);
		Mockito.when(propertiesReaderMock.getAsString(Property.PROJECT_NAME)).thenReturn(PROJECT_NAME);
		
		
	}

}
