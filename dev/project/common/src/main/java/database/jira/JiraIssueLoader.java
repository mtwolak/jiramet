package database.jira;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import database.entity.IssueReporter;
import database.entity.JiraIssue;
import database.manager.DatabaseManager;

public class JiraIssueLoader {

	public JiraIssue loadByReporter(DatabaseManager manager, String reporterName) {
		Criteria criteria = manager.getSession().createCriteria(JiraIssue.class);
		IssueReporter issueReporter = new IssueReporterLoader().load(manager, reporterName);
		return (JiraIssue) criteria.add(Restrictions.eq("issueReporter", issueReporter)).list().get(0);
	}

}
