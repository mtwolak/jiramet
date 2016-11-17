package database.jira;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import database.entity.IssueReporter;
import database.entity.JiraIssue;
import database.manager.DatabaseManager;

public class JiraIssueLoader {

	public JiraIssue loadByReporter(DatabaseManager manager, String reporterName) {
		Session session = manager.getSession();
		Criteria criteria = session.createCriteria(JiraIssue.class);
		IssueReporter issueReporter = new IssueReporterLoader().load(manager, reporterName);
		JiraIssue jiraIssue = (JiraIssue) criteria.add(Restrictions.eq("issueReporter", issueReporter)).list().get(0);
		session.close();
		return jiraIssue;
	}

}
