package org.erp.tarak.worker;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class WorkerUtilities {

	public static Worker getWorkerModel(WorkerService workerService,
			long workerId) {
		Worker worker = workerService.getWorker(workerId);
		return worker;
	}

	public static Worker prepareWorkerModel(WorkerBean workerBean) {
		Worker worker = new Worker();
		worker.setAddress(workerBean.getAddress());
		worker.setMobile(workerBean.getMobile());
		worker.setOpeningBalance(workerBean.getOpeningBalance());
		worker.setWorkerId(workerBean.getWorkerId());
		worker.setWorkerName(workerBean.getWorkerName());
		return worker;
	}

	public static List<WorkerBean> prepareListofWorkerBean(
			List<Worker> workers) {
		List<WorkerBean> beans = null;
		if (workers != null && !workers.isEmpty()) {
			beans = new ArrayList<WorkerBean>();
			WorkerBean bean = null;
			for (Worker worker : workers) {
				bean = new WorkerBean();
				bean.setWorkerId(worker.getWorkerId());
				bean.setWorkerName(worker.getWorkerName());
				bean.setAddress(worker.getAddress());
				bean.setMobile(worker.getMobile());
				bean.setOpeningBalance(worker.getOpeningBalance());
				beans.add(bean);
			}
		}
		return beans;
	}

	public static WorkerBean prepareWorkerBean(long workerId,
			WorkerService workerService) {
		WorkerBean bean = new WorkerBean();
		Worker worker = WorkerUtilities.getWorkerModel(workerService,
				workerId);
		bean.setWorkerId(worker.getWorkerId());
		bean.setWorkerName(worker.getWorkerName());
		bean.setAddress(worker.getAddress());
		bean.setMobile(worker.getMobile());
		bean.setOpeningBalance(worker.getOpeningBalance());
		return bean;
	}

	public static WorkerBean prepareWorkerBean(Worker worker) {
		WorkerBean bean = new WorkerBean();
		if(worker!=null)
		{
			bean.setWorkerId(worker.getWorkerId());
			bean.setWorkerName(worker.getWorkerName());
			bean.setAddress(worker.getAddress());
			bean.setMobile(worker.getMobile());
			bean.setOpeningBalance(worker.getOpeningBalance());
		}
		return bean;
	}

	public static List<WorkerBean> prepareListofWorkerBeans(
			List<Worker> workers) {
		List<WorkerBean> beans = null;
		if (workers != null && !workers.isEmpty()) {
			beans = new LinkedList<WorkerBean>();
			WorkerBean bean = null;
			for (Worker worker : workers) {
				bean = new WorkerBean();
				bean.setWorkerId(worker.getWorkerId());
				bean.setWorkerName(worker.getWorkerName());
				bean.setAddress(worker.getAddress());
				bean.setMobile(worker.getMobile());
				bean.setOpeningBalance(worker.getOpeningBalance());
				beans.add(bean);
			}
		}
		return beans;
	}

}
