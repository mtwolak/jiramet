package database.jira;

import org.hibernate.Criteria;
import org.hibernate.Session;

import database.manager.DatabaseManager;

public abstract class JiraCriteriaDbLoader<T> {
	
	private DatabaseManager databaseManager;

	public JiraCriteriaDbLoader(DatabaseManager databaseManager) {
		this.databaseManager = databaseManager;
	}
	
	protected abstract T setCriteria(Criteria criteria);
	
	protected DatabaseManager getDatabaseManager() {
		return databaseManager;
	}
	
	public T get(Class<T> klazz) {
		Session session = databaseManager.getSession();
		Criteria criteria = session.createCriteria(klazz);
		T objectRet = setCriteria(criteria);
		session.close();
		return objectRet;
	}
	
}
