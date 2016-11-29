package retriever.main;

import jira.JiraWebLogDownloader;
import jira.anonymization.NameRandomizer;
import jira.connector.IssueDownloader;
import jira.project.ProjectData;
import utils.properties.PropertiesReader;
import utils.properties.hibernate.HibernateProductionConfiguration;

public class IssueDownloaderMain implements JiraWebLogDownloader
{
	IssueDownloader id;	
	private PropertiesReader propertiesReader;

	public IssueDownloaderMain(PropertiesReader propertiesReader)
	{
		this.propertiesReader = propertiesReader;
		id = new IssueDownloader(new HibernateProductionConfiguration(propertiesReader));
	}

	@Override
	public void retrieveAllIssues()
	{
		id.retrieveAllIssues();
		NameRandomizer.randomizeAllNames(new HibernateProductionConfiguration(propertiesReader));
	}

	@Override
	public void retrieveIssuesFromProject(ProjectData project)
	{
		id.downloadAllIssuesFromProject(project);
		NameRandomizer.randomizeAllNames(new HibernateProductionConfiguration(propertiesReader));
	}

}
