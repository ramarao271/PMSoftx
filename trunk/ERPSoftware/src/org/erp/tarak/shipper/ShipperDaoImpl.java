package org.erp.tarak.shipper;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Dinesh Rajput
 * 
 */
@Repository("shipperDao")
public class ShipperDaoImpl implements ShipperDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void addShipper(Shipper shipper) {
		if (shipper.getShipperId() > 0) {
			sessionFactory.getCurrentSession().update(shipper);
		} else {
			sessionFactory.getCurrentSession().save(shipper);
		}

	}

	@SuppressWarnings("unchecked")
	public List<Shipper> listShippers() {
		return (List<Shipper>) sessionFactory.getCurrentSession()
				.createCriteria(Shipper.class).list();
	}

	public Shipper getShipper(long empid) {
		Shipper s=(Shipper) sessionFactory.getCurrentSession().get(
				Shipper.class, empid);
		return s;
	}

	public void deleteShipper(Shipper shipper) {
		sessionFactory
				.getCurrentSession()
				.createQuery(
						"DELETE FROM Shipper WHERE shipperId = "
								+ shipper.getShipperId()).executeUpdate();
	}

	@Override
	public List<Shipper> listShippersbyCompanyName(String companyName) {
		String c=companyName;
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				Shipper.class);
		crit.add(Restrictions.eq("companyName", c));
		List results = crit.list();
		return results;
		
	}

	@Override
	public List<Shipper> listShippersbyCompanyNameRegex(String search) {
		String c="%"+search+"%";
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				Shipper.class);
		crit.add(Restrictions.ilike("companyName", c));
		List results = crit.list();
		return results;
	}
}
