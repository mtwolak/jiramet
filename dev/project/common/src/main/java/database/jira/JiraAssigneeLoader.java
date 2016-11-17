package database.jira;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import database.entity.Assignee;
import database.manager.DatabaseManager;

public class JiraAssigneeLoader {
	
	public Assignee loadByName(DatabaseManager manager, String reporterName) {
		Session session = manager.getSession();
		Criteria criteria = session.createCriteria(Assignee.class);
		Assignee assignee = (Assignee) criteria.add(Restrictions.eq("name", reporterName)).list().get(0);
		session.close();
		return assignee;
	}
	
}
