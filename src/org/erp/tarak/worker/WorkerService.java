package org.erp.tarak.worker;

import java.util.List;


public interface WorkerService {

	public void addWorker(Worker worker);

	public List<Worker> listWorkers();

	public Worker getWorker(long workerId);

	public void deleteWorker(Worker worker);
	
	public List<Worker> listWorkersbyCompanyName(String companyName);

	public List<Worker> listWorkersbyCompanyNameRegex(String search);

	public List<Object[]> getWorkerTransactions(long workerId, String finYear);
}
