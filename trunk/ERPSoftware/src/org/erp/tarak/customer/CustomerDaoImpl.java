package org.erp.tarak.customer;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("customerDao")
public class CustomerDaoImpl implements CustomerDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void addCustomer(Customer customer) {
		if (customer.getCustomerId() > 0) {
			sessionFactory.getCurrentSession().update(customer);
		} else {
			sessionFactory.getCurrentSession().save(customer);
		}
		
	}

	@SuppressWarnings("unchecked")
	public List<Customer> listCustomers() {
		return (List<Customer>) sessionFactory.getCurrentSession()
				.createCriteria(Customer.class).list();
	}

	public Customer getCustomer(long empid) {
		 Customer customer=(Customer) sessionFactory.getCurrentSession().get(
				Customer.class, empid);
		 return customer;
	}

	public void deleteCustomer(Customer customer) {
		sessionFactory
				.getCurrentSession()
				.createQuery(
						"DELETE FROM Customer WHERE customerId = "
								+ customer.getCustomerId()).executeUpdate();
	}

	@Override
	public List<Customer> listCustomersbyCompanyName(String companyName) {
		String c=companyName;
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				Customer.class);
		crit.add(Restrictions.eq("companyName", c));
		List results = crit.list();
		return results;
		
	}

	@Override
	public List<Customer> listCustomersbyCompanyNameRegex(String search) {
		String c="%"+search+"%";
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				Customer.class);
		crit.add(Restrictions.ilike("companyName", c));
		List results = crit.list();
		return results;
	}
}
