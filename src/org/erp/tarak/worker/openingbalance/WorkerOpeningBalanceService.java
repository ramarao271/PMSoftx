package org.erp.tarak.worker.openingbalance;

import java.util.List;


public interface WorkerOpeningBalanceService {
	public void addWorkerOpeningBalance(WorkerOpeningBalance workerOpeningBalance);

	public void addWorkerOpeningBalances(List<WorkerOpeningBalance> workerOpeningBalances);
	
	public List<WorkerOpeningBalance> listWorkerOpeningBalances();
	
	public WorkerOpeningBalance getWorkerOpeningBalance(int workerOpeningBalanceId);
	
	public void deleteWorkerOpeningBalance(WorkerOpeningBalance workerOpeningBalance);
	
	public void deleteWorkerOpeningBalances(List<WorkerOpeningBalance> workerOpeningBalances);

	public WorkerOpeningBalance getWorkerOpeningBalance(String finYear,
			long workerId);

}
