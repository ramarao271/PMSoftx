package org.erp.tarak.worker.openingbalance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service("workerOpeningBalanceService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class WorkerOpeningBalanceServiceImpl implements WorkerOpeningBalanceService {

	@Autowired
	private WorkerOpeningBalanceDao workerOpeningBalanceDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addWorkerOpeningBalance(WorkerOpeningBalance workerOpeningBalance) {
		workerOpeningBalanceDao.addWorkerOpeningBalance(workerOpeningBalance);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addWorkerOpeningBalances(List<WorkerOpeningBalance> workerOpeningBalances) {
		workerOpeningBalanceDao.addWorkerOpeningBalances(workerOpeningBalances);
	}
	
	public List<WorkerOpeningBalance> listWorkerOpeningBalances() {
		return workerOpeningBalanceDao.listWorkerOpeningBalances();
	}

	public WorkerOpeningBalance getWorkerOpeningBalance(int workerOpeningBalanceId) {
		return workerOpeningBalanceDao.getWorkerOpeningBalance(workerOpeningBalanceId);
	}
	
	public void deleteWorkerOpeningBalance(WorkerOpeningBalance workerOpeningBalance) {
		workerOpeningBalanceDao.deleteWorkerOpeningBalance(workerOpeningBalance);
	}
	public void deleteWorkerOpeningBalances(List<WorkerOpeningBalance> workerOpeningBalances) {
		workerOpeningBalanceDao.deleteWorkerOpeningBalances(workerOpeningBalances);
	}

	@Override
	public WorkerOpeningBalance getWorkerOpeningBalance(String finYear,
			long workerId) {
		return workerOpeningBalanceDao.getWorkerOpeningBalance(finYear,workerId);
	}

}
