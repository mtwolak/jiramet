package database.jira;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import database.entity.IssueReporter;
import database.manager.DatabaseManager;

public class IssueReporterLoader {

	public IssueReporter load(DatabaseManager manager, String reporterName) {
		Session session = manager.getSession();
		Criteria criteria = session.createCriteria(IssueReporter.class);
		IssueReporter issueReporter = (IssueReporter) criteria.add(Restrictions.eq("fullName", reporterName)).list().get(0);
		session.close();
		return issueReporter;
	}

}
