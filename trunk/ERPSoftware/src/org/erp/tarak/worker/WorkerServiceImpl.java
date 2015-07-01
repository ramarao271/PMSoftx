package org.erp.tarak.worker;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service("workerService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class WorkerServiceImpl implements WorkerService {

	@Autowired
	private WorkerDao workerDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addWorker(Worker worker) {
		workerDao.addWorker(worker);
	}
	
	public List<Worker> listWorkers() {
		return workerDao.listWorkers();
	}

	public Worker getWorker(long workerId) {
		return workerDao.getWorker(workerId);
	}
	
	public void deleteWorker(Worker worker) {
		workerDao.deleteWorker(worker);
	}

	@Override
	public List<Worker> listWorkersbyCompanyName(String companyName) {
		return workerDao.listWorkersbyCompanyName(companyName);
	}

	@Override
	public List<Worker> listWorkersbyCompanyNameRegex(String search) {
		return workerDao.listWorkersbyCompanyNameRegex(search);
	}

}
