package database.jira;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import database.entity.IssueReporter;
import database.entity.JiraIssue;
import database.manager.DatabaseManager;

/**
 * Query for searching jira issues raised by issue reporters
 *
 */
public class JiraIssueLoader extends JiraCriteriaDbLoader<JiraIssue> {

	private String reporterName;

	/**
	 * Query for searching jira issues in given database for given issue reporter
	 * @param databaseManager database
	 * @param reporterName issue reporter
	 */
	public JiraIssueLoader(DatabaseManager databaseManager, String reporterName) {
		super(databaseManager);
		this.reporterName = reporterName;
	}

	@Override
	protected JiraIssue setCriteria(Criteria criteria) {
		IssueReporter issueReporter = new IssueReporterLoader(getDatabaseManager(), reporterName).get(IssueReporter.class);
		return (JiraIssue) criteria.add(Restrictions.eq("issueReporter", issueReporter)).list().get(0);
	}

}
