package br.org.bueno.application.socket.util;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.org.bueno.application.socket.entity.User;

public class H2Test {

	public static void main(String[] args) {

		User user =  new User("Rambo", 25, 100, 20, 10);

		Transaction transaction = null;

		try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {

			transaction = session.beginTransaction();
			session.save(user);

			transaction.commit();

		} catch (Exception e) {
			if(transaction == null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			List <User> users = session.createQuery("from User", User.class).list();
			users.forEach(s -> System.out.println(s.getName()));
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}

	}

}
