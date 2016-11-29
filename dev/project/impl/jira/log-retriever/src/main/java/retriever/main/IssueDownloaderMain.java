package retriever.main;

import jira.JiraWebLogDownloader;
import jira.anonymization.NameRandomizer;
import jira.connector.IssueDownloader;
import utils.properties.PropertiesReader;
import utils.properties.hibernate.HibernateProductionConfiguration;

public class IssueDownloaderMain implements JiraWebLogDownloader
{
	
	private PropertiesReader propertiesReader;

	public IssueDownloaderMain(PropertiesReader propertiesReader)
	{
		this.propertiesReader = propertiesReader;
	}

	@Override
	public void retrieveAllIssues()
	{
		IssueDownloader id = new IssueDownloader(new HibernateProductionConfiguration(propertiesReader));
		id.retrieveAllIssues();
		NameRandomizer.randomizeAllNames(new HibernateProductionConfiguration(propertiesReader));
	}

}
