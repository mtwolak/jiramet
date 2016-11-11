package database.manager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class DatabaseManager {
	
	private static SessionFactory factory;
	private static final String PATH_TO_SETTINGS = "/resources/hibernate.cfg.xml";
	
	public static void persist(Object entity) {
		Configuration config = new Configuration().configure(PATH_TO_SETTINGS);
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
		
		factory = config.buildSessionFactory(serviceRegistry);
		
		Session session = factory.openSession();
		
		Transaction  tx = session.beginTransaction();
		session.save(entity);
		tx.commit();
	}

}
