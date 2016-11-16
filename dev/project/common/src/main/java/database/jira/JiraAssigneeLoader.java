package database.jira;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import database.entity.Assignee;
import database.manager.DatabaseManager;

public class JiraAssigneeLoader {
	
	public Assignee loadByName(DatabaseManager manager, String reporterName) {
		Criteria criteria = manager.getSession().createCriteria(Assignee.class);
		return (Assignee) criteria.add(Restrictions.eq("name", reporterName)).list().get(0);
	}
	
}
