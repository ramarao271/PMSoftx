package org.erp.tarak.worker;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("workerDao")
public class WorkerDaoImpl implements WorkerDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void addWorker(Worker worker) {
		if (worker.getWorkerId() > 0) {
			sessionFactory.getCurrentSession().update(worker);
		} else {
			sessionFactory.getCurrentSession().save(worker);
		}

	}

	@SuppressWarnings("unchecked")
	public List<Worker> listWorkers() {
		return (List<Worker>) sessionFactory.getCurrentSession()
				.createCriteria(Worker.class).list();
	}

	public Worker getWorker(long empid) {
		Worker s=(Worker) sessionFactory.getCurrentSession().get(
				Worker.class, empid);
				sessionFactory.getCurrentSession().flush();
		return s;
	}

	public void deleteWorker(Worker worker) {
		sessionFactory
				.getCurrentSession()
				.createQuery(
						"DELETE FROM Worker WHERE workerId = "
								+ worker.getWorkerId()).executeUpdate();
	}

	@Override
	public List<Worker> listWorkersbyCompanyName(String companyName) {
		String c=companyName;
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				Worker.class);
		crit.add(Restrictions.eq("workerName", c));
		List results = crit.list();
		return results;
		
	}

	@Override
	public List<Worker> listWorkersbyCompanyNameRegex(String search) {
		String c="%"+search+"%";
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				Worker.class);
		crit.add(Restrictions.ilike("workerName", c));
		List results = crit.list();
		return results;
	}

	@Override
	public List<Object[]> getWorkerTransactions(long workerId, String finYear) {
		Query query=null;
		String hql="select productionorderid as Id,poo_date as date,totalcost as OrderCost,0 as InvoiceCost from productionOrder where worker_id="+workerId+" union select productionInvoiceId as Id,poi_date as date,0 as OrderCost,totalCost as InvoiceCost from productionInvoice where worker_id="+workerId+" order by date;";
		query = sessionFactory.getCurrentSession().createSQLQuery(hql).addScalar("Id").addScalar("date").addScalar("OrderCost").addScalar("InvoiceCost");
		List results = query.list();
		return results;

	}
}
