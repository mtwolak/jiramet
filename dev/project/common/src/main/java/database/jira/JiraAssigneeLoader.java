package database.jira;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import database.entity.Assignee;
import database.manager.DatabaseManager;

public class JiraAssigneeLoader extends JiraCriteriaDbLoader<Assignee>{
	
	private String reporterName;

	public JiraAssigneeLoader(DatabaseManager databaseManager, String reporterName) {
		super(databaseManager);
		this.reporterName = reporterName;
	}

	@Override
	protected Assignee setCriteria(Criteria criteria) {
		return (Assignee) criteria.add(Restrictions.eq("name", reporterName)).list().get(0);
	}
	
}
