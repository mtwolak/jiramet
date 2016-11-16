package database.manager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class DatabaseManager {

	private  SessionFactory factory;
	private  Session session;
	private static final String PATH_TO_SETTINGS = "/resources/hibernate.cfg.xml";

	public void init() {
		Configuration config = new Configuration().configure(PATH_TO_SETTINGS);
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(config.getProperties())
				.buildServiceRegistry();
		factory = config.buildSessionFactory(serviceRegistry);

		session = factory.openSession();
	}

	public void close() {
		session.close();
	}

	public void persist(Object entity) {
		Transaction tx = session.beginTransaction();
		session.save(entity);
		tx.commit();
	}
	
	public void merge(Object entity) {
		Transaction tx = session.beginTransaction();
		session.merge(entity);
		tx.commit();
	}

	public Session getSession() {
		return session;
	}

}
