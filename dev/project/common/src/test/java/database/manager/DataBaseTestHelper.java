package database.manager;

import org.hibernate.Query;
import org.hibernate.Session;

public class DataBaseTestHelper {

	private static final String[] ALL_TABLES = { "ASSIGNED_ISSUE", "ASSIGNEE", "ISSUE_COMMENT", "ISSUE_PRIORITY",
			"ISSUE_REPORTER", "ISSUE_RESOLUTION", "ISSUE_STATUS", "ISSUE_TYPE", "JIRA_ISSUE", "JIRA_PROJECT" };

	public static void truncateAllTables(Session session) {
		setCheckConstraints(session, false);
		truncateTables(session);
		setCheckConstraints(session, true);
	}

	private static void truncateTables(Session session) {
		for(String tableName : ALL_TABLES) {
			Query queryTruncate = session.createSQLQuery("truncate table " + tableName);
			queryTruncate.executeUpdate();
		}
	}

	private static void setCheckConstraints(Session session, boolean doNeedCheckConstraint) {
		int needCheckConstraint = doNeedCheckConstraint ? 1 : 0;
		Query queryConstraintOff = session.createSQLQuery("SET FOREIGN_KEY_CHECKS = " + needCheckConstraint);
		queryConstraintOff.executeUpdate();
	}

}
