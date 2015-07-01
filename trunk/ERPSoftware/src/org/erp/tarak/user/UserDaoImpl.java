package org.erp.tarak.user;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void addUser(User user) {
		sessionFactory.getCurrentSession().saveOrUpdate(user);
	}

	@SuppressWarnings("unchecked")
	public List<User> listUsers() {
		return (List<User>) sessionFactory.getCurrentSession()
				.createCriteria(User.class).list();
	}

	public User getUser(String userId) {
		return (User) sessionFactory.getCurrentSession().get(User.class, userId);
	}

	public void deleteUser(User user) {
		sessionFactory
				.getCurrentSession()
				.createQuery(
						"DELETE FROM User WHERE username = " + user.getUsername())
				.executeUpdate();
	}

}
