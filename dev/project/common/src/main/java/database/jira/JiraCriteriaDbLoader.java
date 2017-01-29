package database.jira;

import org.hibernate.Criteria;
import org.hibernate.Session;

import database.manager.DatabaseManager;

/**
 * Class for getting information from database
 *
 * @param <T> expected class to be returned from database
 */
public abstract class JiraCriteriaDbLoader<T> {
	
	private DatabaseManager databaseManager;

	/**
	 * Constructs new query for given database
	 * @param databaseManager database
	 */
	public JiraCriteriaDbLoader(DatabaseManager databaseManager) {
		this.databaseManager = databaseManager;
	}
	
	protected abstract T setCriteria(Criteria criteria);
	
	protected DatabaseManager getDatabaseManager() {
		return databaseManager;
	}
	
	/**
	 * Method for retrieving information from database
	 * @param klazz expected class to be returned from database query
	 * @return requested info from database
	 */
	public T get(Class<T> klazz) {
		Session session = databaseManager.getSession();
		Criteria criteria = session.createCriteria(klazz);
		T objectRet = setCriteria(criteria);
		session.close();
		return objectRet;
	}
	
}
