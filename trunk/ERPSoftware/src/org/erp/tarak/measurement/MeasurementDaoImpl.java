package org.erp.tarak.measurement;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("measurementDao")
public class MeasurementDaoImpl implements MeasurementDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void addMeasurement(Measurement measurement) {
		if (measurement.getMeasurementId() > 0) {
			sessionFactory.getCurrentSession().update(measurement);
		} else {
			sessionFactory.getCurrentSession().save(measurement);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Measurement> listMeasurements() {
		return (List<Measurement>) sessionFactory.getCurrentSession()
				.createCriteria(Measurement.class).list();
	}

	public Measurement getMeasurement(long measurementId) {
		return (Measurement) sessionFactory.getCurrentSession().get(
				Measurement.class, measurementId);
	}

	public void deleteMeasurement(Measurement measurement) {
		sessionFactory
				.getCurrentSession()
				.createQuery(
						"DELETE FROM Measurement WHERE measurementId = "
								+ measurement.getMeasurementId())
				.executeUpdate();
	}

	@Override
	public Measurement getMeasurementByName(String measurementName) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				Measurement.class);
		crit.add(Restrictions.eq("measurementName", measurementName));
		List results = crit.list();
		if (results != null && results.size() > 0) {
			return (Measurement) results.get(0);
		} else {
			return null;
		}
	}

}
