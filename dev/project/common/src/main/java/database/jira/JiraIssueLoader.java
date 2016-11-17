package database.jira;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import database.entity.IssueReporter;
import database.entity.JiraIssue;
import database.manager.DatabaseManager;

public class JiraIssueLoader extends JiraCriteriaDbLoader<JiraIssue> {

	private String reporterName;

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
