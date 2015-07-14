package org.erp.tarak.worker.openingbalance;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("workerOpeningBalanceDao")
public class WorkerOpeningBalanceDaoImpl implements WorkerOpeningBalanceDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void addWorkerOpeningBalance(WorkerOpeningBalance workerOpeningBalance) {
		if (workerOpeningBalance.getWorkerOpeningBalanceId() > 0) {
			sessionFactory.getCurrentSession().update(workerOpeningBalance);
		} else {
			sessionFactory.getCurrentSession().save(workerOpeningBalance);
		}
	}

	public void addWorkerOpeningBalances(List<WorkerOpeningBalance> workerOpeningBalances) {
		if (workerOpeningBalances != null) {
			for (WorkerOpeningBalance workerOpeningBalance : workerOpeningBalances) {
				addWorkerOpeningBalance(workerOpeningBalance);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<WorkerOpeningBalance> listWorkerOpeningBalances() {
		return (List<WorkerOpeningBalance>) sessionFactory.getCurrentSession()
				.createCriteria(WorkerOpeningBalance.class).list();
	}

	public WorkerOpeningBalance getWorkerOpeningBalance(long empid) {
		return (WorkerOpeningBalance) sessionFactory.getCurrentSession().get(
				WorkerOpeningBalance.class, empid);
	}

	public void deleteWorkerOpeningBalance(WorkerOpeningBalance workerOpeningBalance) {
		sessionFactory
				.getCurrentSession()
				.createQuery(
						"DELETE FROM WorkerOpeningBalance WHERE accountId = "
								+ workerOpeningBalance.getWorkerOpeningBalanceId()).executeUpdate();
	}

	public void deleteWorkerOpeningBalances(List<WorkerOpeningBalance> workerOpeningBalances) {
		for (WorkerOpeningBalance workerOpeningBalance : workerOpeningBalances) {
			deleteWorkerOpeningBalance(workerOpeningBalance);
		}
	}

	@Override
	public WorkerOpeningBalance getWorkerOpeningBalance(String finYear,
			long workerId) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				WorkerOpeningBalance.class);
		crit.add(Restrictions.eq("financialYear", finYear));
		crit.add(Restrictions.eq("workerId", workerId));
		List results = crit.list();
		if(results!=null && results.size()>0)
		{
			return (WorkerOpeningBalance)results.get(0);
		}
		else
		{
			return null;
		}
	}
}
