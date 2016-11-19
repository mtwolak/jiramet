package jira.connector;

import database.manager.DatabaseManager;

public class DataBaseTestHelper {

	private static final String[] ALL_TABLES = { "ASSIGNED_ISSUE", "ASSIGNEE", "ISSUE_COMMENT", "ISSUE_PRIORITY",
			"ISSUE_REPORTER", "ISSUE_RESOLUTION", "ISSUE_TYPE", "JIRA_ISSUE", "JIRA_PROJECT" };

	public static void truncateAllTables(DatabaseManager databaseManager) {
		setCheckConstraints(databaseManager, false);
		truncateTables(databaseManager);
		setCheckConstraints(databaseManager, true);
	}

	private static void truncateTables(DatabaseManager databaseManager) {
		for (String tableName : ALL_TABLES) {
			databaseManager.executeStringSql("truncate table " + tableName);
		}
	}

	private static void setCheckConstraints(DatabaseManager databaseManager, boolean doNeedCheckConstraint) {
		int needCheckConstraint = doNeedCheckConstraint ? 1 : 0;
		databaseManager.executeStringSql("SET FOREIGN_KEY_CHECKS = " + needCheckConstraint);
	}

}
