package org.erp.tarak.shipper.openingbalance;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("shipperOpeningBalanceDao")
public class ShipperOpeningBalanceDaoImpl implements ShipperOpeningBalanceDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void addShipperOpeningBalance(ShipperOpeningBalance shipperOpeningBalance) {
		if (shipperOpeningBalance.getShipperOpeningBalanceId() > 0) {
			sessionFactory.getCurrentSession().update(shipperOpeningBalance);
		} else {
			sessionFactory.getCurrentSession().save(shipperOpeningBalance);
		}
	}

	public void addShipperOpeningBalances(List<ShipperOpeningBalance> shipperOpeningBalances) {
		if (shipperOpeningBalances != null) {
			for (ShipperOpeningBalance shipperOpeningBalance : shipperOpeningBalances) {
				addShipperOpeningBalance(shipperOpeningBalance);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<ShipperOpeningBalance> listShipperOpeningBalances() {
		return (List<ShipperOpeningBalance>) sessionFactory.getCurrentSession()
				.createCriteria(ShipperOpeningBalance.class).list();
	}

	public ShipperOpeningBalance getShipperOpeningBalance(long empid) {
		return (ShipperOpeningBalance) sessionFactory.getCurrentSession().get(
				ShipperOpeningBalance.class, empid);
	}

	public void deleteShipperOpeningBalance(ShipperOpeningBalance shipperOpeningBalance) {
		sessionFactory
				.getCurrentSession()
				.createQuery(
						"DELETE FROM ShipperOpeningBalance WHERE accountId = "
								+ shipperOpeningBalance.getShipperOpeningBalanceId()).executeUpdate();
	}

	public void deleteShipperOpeningBalances(List<ShipperOpeningBalance> shipperOpeningBalances) {
		for (ShipperOpeningBalance shipperOpeningBalance : shipperOpeningBalances) {
			deleteShipperOpeningBalance(shipperOpeningBalance);
		}
	}

	@Override
	public ShipperOpeningBalance getShipperOpeningBalance(String finYear,
			long shipperId) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				ShipperOpeningBalance.class);
		crit.add(Restrictions.eq("financialYear", finYear));
		crit.add(Restrictions.eq("shipperId", shipperId));
		List results = crit.list();
		if(results!=null && results.size()>0)
		{
			return (ShipperOpeningBalance)results.get(0);
		}
		else
		{
			return null;
		}
	}
}
