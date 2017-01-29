package database.jira;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import database.entity.Assignee;
import database.manager.DatabaseManager;

/**
 * Query for search jira assignee 
 *
 */
public class JiraAssigneeLoader extends JiraCriteriaDbLoader<Assignee>{
	
	private String reporterName;

	/**
	 * Constructs query for given database with jira assignee names
	 * @param databaseManager database
	 * @param reporterName searched reporter name
	 */
	public JiraAssigneeLoader(DatabaseManager databaseManager, String reporterName) {
		super(databaseManager);
		this.reporterName = reporterName;
	}

	@Override
	protected Assignee setCriteria(Criteria criteria) {
		return (Assignee) criteria.add(Restrictions.eq("name", reporterName)).list().get(0);
	}
	
}
