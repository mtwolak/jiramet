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

	public IssueDownloaderMain(PropertiesReader propertiesReader)
	{
		super(propertiesReader);
		id = new IssueDownloader(new HibernateProductionConfiguration(propertiesReader));
	}

	@Override
	protected void retrieveAllIssues()
	{
		id.retrieveAllIssues();
		NameRandomizer.randomizeAllNames(new HibernateProductionConfiguration(getPropertiesReader()));
	}

	@Override
	protected void retrieveIssuesFromProject(ProjectData project)
	{
		id.downloadAllIssuesFromProject(project);
		NameRandomizer.randomizeAllNames(new HibernateProductionConfiguration(getPropertiesReader()));
	}

}
