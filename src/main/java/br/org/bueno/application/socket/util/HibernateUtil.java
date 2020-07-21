package br.org.bueno.application.socket.util;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import br.org.bueno.application.socket.entity.User;

public class HibernateUtil {

	private static StandardServiceRegistry registry;
	private static SessionFactory sessionFactory;
	private static boolean saveOK;

	public static SessionFactory getSessionFactory() {

		if (sessionFactory == null) {
			try {
				registry = new StandardServiceRegistryBuilder().configure().build();

				MetadataSources sources = new MetadataSources(registry);

				Metadata metadata = sources.getMetadataBuilder().build();

				sessionFactory = metadata.getSessionFactoryBuilder().build();

			} catch (Exception e) {
				e.printStackTrace();
				if (registry != null) {
					StandardServiceRegistryBuilder.destroy(registry);
				}
			}
		}

		return sessionFactory;
	}

	public static void shutdown() {
		if (registry != null) {
			StandardServiceRegistryBuilder.destroy(registry);
		}
	}

	public static Transaction saveUser(User user) {
		Transaction transaction = null;

		try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {

			transaction = session.beginTransaction();
			session.save(user);

			transaction.commit();
			saveOK = true;
		} catch (Exception e) {
			if (transaction == null) {
				transaction.rollback();
			}
			e.printStackTrace();
			saveOK = false;
		}
		return transaction;
	}

	public static void showUsers(Transaction transaction) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			List<User> users = session.createQuery("from User", User.class).list();
			users.forEach(s -> System.out.println(s.getName()));
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	public static boolean isSaveOK() {
		return saveOK;
	}

	public static void setSaveOK(boolean saveOK) {
		saveOK = saveOK;
	}

}
