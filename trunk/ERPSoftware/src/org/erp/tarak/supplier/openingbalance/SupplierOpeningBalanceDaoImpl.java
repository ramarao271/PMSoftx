package org.erp.tarak.supplier.openingbalance;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("supplierOpeningBalanceDao")
public class SupplierOpeningBalanceDaoImpl implements SupplierOpeningBalanceDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void addSupplierOpeningBalance(SupplierOpeningBalance supplierOpeningBalance) {
		if (supplierOpeningBalance.getSupplierOpeningBalanceId() > 0) {
			sessionFactory.getCurrentSession().update(supplierOpeningBalance);
		} else {
			sessionFactory.getCurrentSession().save(supplierOpeningBalance);
		}
	}

	public void addSupplierOpeningBalances(List<SupplierOpeningBalance> supplierOpeningBalances) {
		if (supplierOpeningBalances != null) {
			for (SupplierOpeningBalance supplierOpeningBalance : supplierOpeningBalances) {
				addSupplierOpeningBalance(supplierOpeningBalance);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<SupplierOpeningBalance> listSupplierOpeningBalances() {
		return (List<SupplierOpeningBalance>) sessionFactory.getCurrentSession()
				.createCriteria(SupplierOpeningBalance.class).list();
	}

	public SupplierOpeningBalance getSupplierOpeningBalance(long empid) {
		return (SupplierOpeningBalance) sessionFactory.getCurrentSession().get(
				SupplierOpeningBalance.class, empid);
	}

	public void deleteSupplierOpeningBalance(SupplierOpeningBalance supplierOpeningBalance) {
		sessionFactory
				.getCurrentSession()
				.createQuery(
						"DELETE FROM SupplierOpeningBalance WHERE accountId = "
								+ supplierOpeningBalance.getSupplierOpeningBalanceId()).executeUpdate();
	}

	public void deleteSupplierOpeningBalances(List<SupplierOpeningBalance> supplierOpeningBalances) {
		for (SupplierOpeningBalance supplierOpeningBalance : supplierOpeningBalances) {
			deleteSupplierOpeningBalance(supplierOpeningBalance);
		}
	}

	@Override
	public SupplierOpeningBalance getSupplierOpeningBalance(String finYear,
			long supplierId) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				SupplierOpeningBalance.class);
		crit.add(Restrictions.eq("financialYear", finYear));
		crit.add(Restrictions.eq("supplierId", supplierId));
		List results = crit.list();
		if(results!=null && results.size()>0)
		{
			return (SupplierOpeningBalance)results.get(0);
		}
		else
		{
			return null;
		}
	}
}
