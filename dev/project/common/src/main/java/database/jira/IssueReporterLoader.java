package database.jira;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import database.entity.IssueReporter;
import database.manager.DatabaseManager;

public class IssueReporterLoader extends JiraCriteriaDbLoader<IssueReporter>{
	
	private String reporterName;

	public IssueReporterLoader(DatabaseManager databaseManager, String reporterName) {
		super(databaseManager);
		this.reporterName = reporterName;
	}

	@Override
	protected IssueReporter setCriteria(Criteria criteria) {
		return (IssueReporter) criteria.add(Restrictions.eq("fullName", reporterName)).list().get(0);
	}

}
