package database.jira;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import database.entity.IssueReporter;
import database.manager.DatabaseManager;

public class IssueReporterLoader {

	public IssueReporter load(DatabaseManager manager, String reporterName) {
		Criteria criteria = manager.getSession().createCriteria(IssueReporter.class);
		return (IssueReporter) criteria.add(Restrictions.eq("fullName", reporterName)).list().get(0);
	}

}
