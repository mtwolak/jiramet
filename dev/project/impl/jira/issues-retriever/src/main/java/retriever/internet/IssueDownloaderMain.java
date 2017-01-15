package retriever.internet;

import jira.JiraWebLogDownloader;
import jira.anonymization.NameRandomizer;
import jira.connector.IssueDownloader;
import jira.project.ProjectData;
import utils.properties.PropertiesReader;
import utils.properties.hibernate.HibernateProductionConfiguration;

public class IssueDownloaderMain extends JiraWebLogDownloader
{
	private IssueDownloader id;	

	/**
	 * Class constructor. Main class responsible for retrieving issues from JIRA project and placing them in the local database
	 * 
	 * @param propertiesReader properties reader, grants access to system configuration variables
	 * @see PropertiesReader
	 */
	public IssueDownloaderMain(PropertiesReader propertiesReader)
	{
		super(propertiesReader);
		id = new IssueDownloader(new HibernateProductionConfiguration(propertiesReader));
	}

	@Override
	protected void retrieveIssuesFromProject(ProjectData project)
	{
		id.downloadAllIssuesFromProject(project);
		NameRandomizer.randomizeAllNames(new HibernateProductionConfiguration(getPropertiesReader()));
	}

}
