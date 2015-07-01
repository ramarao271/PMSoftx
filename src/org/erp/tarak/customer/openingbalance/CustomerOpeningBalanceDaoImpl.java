package org.erp.tarak.customer.openingbalance;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("customerOpeningBalanceDao")
public class CustomerOpeningBalanceDaoImpl implements CustomerOpeningBalanceDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void addCustomerOpeningBalance(CustomerOpeningBalance customerOpeningBalance) {
		if (customerOpeningBalance.getCustomerOpeningBalanceId() > 0) {
			sessionFactory.getCurrentSession().update(customerOpeningBalance);
		} else {
			sessionFactory.getCurrentSession().save(customerOpeningBalance);
		}
	}

	public void addCustomerOpeningBalances(List<CustomerOpeningBalance> customerOpeningBalances) {
		if (customerOpeningBalances != null) {
			for (CustomerOpeningBalance customerOpeningBalance : customerOpeningBalances) {
				addCustomerOpeningBalance(customerOpeningBalance);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<CustomerOpeningBalance> listCustomerOpeningBalances() {
		return (List<CustomerOpeningBalance>) sessionFactory.getCurrentSession()
				.createCriteria(CustomerOpeningBalance.class).list();
	}

	public CustomerOpeningBalance getCustomerOpeningBalance(long empid) {
		return (CustomerOpeningBalance) sessionFactory.getCurrentSession().get(
				CustomerOpeningBalance.class, empid);
	}

	public void deleteCustomerOpeningBalance(CustomerOpeningBalance customerOpeningBalance) {
		sessionFactory
				.getCurrentSession()
				.createQuery(
						"DELETE FROM CustomerOpeningBalance WHERE accountId = "
								+ customerOpeningBalance.getCustomerOpeningBalanceId()).executeUpdate();
	}

	public void deleteCustomerOpeningBalances(List<CustomerOpeningBalance> customerOpeningBalances) {
		for (CustomerOpeningBalance customerOpeningBalance : customerOpeningBalances) {
			deleteCustomerOpeningBalance(customerOpeningBalance);
		}
	}

	@Override
	public CustomerOpeningBalance getCustomerOpeningBalance(String finYear,
			long customerId) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				CustomerOpeningBalance.class);
		crit.add(Restrictions.eq("financialYear", finYear));
		crit.add(Restrictions.eq("customerId", customerId));
		List results = crit.list();
		if(results!=null && results.size()>0)
		{
			return (CustomerOpeningBalance)results.get(0);
		}
		else
		{
			return null;
		}
	}
}
