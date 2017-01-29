package database.jira;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import database.entity.IssueReporter;
import database.manager.DatabaseManager;
/**
 * Database query for retrieving issue reporters with given name
 */
public class IssueReporterLoader extends JiraCriteriaDbLoader<IssueReporter>{
	
	private String reporterName;

	/**
	 * Constructs new query to given database with reporter to be founded
	 * @param databaseManager database
	 * @param reporterName reporter name to be founded in database
	 */
	public IssueReporterLoader(DatabaseManager databaseManager, String reporterName) {
		super(databaseManager);
		this.reporterName = reporterName;
	}

	@Override
	protected IssueReporter setCriteria(Criteria criteria) {
		return (IssueReporter) criteria.add(Restrictions.eq("fullName", reporterName)).list().get(0);
	}

}
