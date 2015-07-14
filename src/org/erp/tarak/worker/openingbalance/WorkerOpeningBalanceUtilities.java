package org.erp.tarak.worker.openingbalance;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.erp.tarak.library.ERPConstants;

public class WorkerOpeningBalanceUtilities {

	public static List<WorkerOpeningBalance> saveWorkerOpeningBalances(
			WorkerOpeningBalanceService workerOpeningBalanceService,
			List<WorkerOpeningBalance> workerOpeningBalances) {
		workerOpeningBalanceService.addWorkerOpeningBalances(workerOpeningBalances);
		return workerOpeningBalances;
	}

	public static List<WorkerOpeningBalance> populateWorkerOpeningBalances(
			List<WorkerOpeningBalanceBean> workerOpeningBalanceBeans) {
		List<WorkerOpeningBalance> workerOpeningBalances = new LinkedList<WorkerOpeningBalance>();
		if (workerOpeningBalanceBeans != null) {
			for (WorkerOpeningBalanceBean workerOpeningBalanceBean : workerOpeningBalanceBeans) {
				if (workerOpeningBalanceBean.getWorkerOpeningBalanceId() != 0
						&& !"".equals(workerOpeningBalanceBean.getWorkerOpeningBalanceId())) {
					WorkerOpeningBalance workerOpeningBalance = new WorkerOpeningBalance();
					workerOpeningBalance.setCurrentBalance(workerOpeningBalanceBean.getCurrentBalance());
					workerOpeningBalance.setWorkerId(workerOpeningBalanceBean.getWorkerId());
					workerOpeningBalance.setWorkerOpeningBalanceId(workerOpeningBalanceBean.getWorkerOpeningBalanceId());
					workerOpeningBalance.setFinancialYear(workerOpeningBalanceBean.getFinancialYear());
					workerOpeningBalance.setOpeningBalance(workerOpeningBalanceBean.getOpeningBalance());
					workerOpeningBalances.add(workerOpeningBalance);
				}
			}
		}
		return workerOpeningBalances;
	}

	public static List<WorkerOpeningBalanceBean> prepareWorkerOpeningBalanceBean(
			List<WorkerOpeningBalance> workerOpeningBalances) {
		List<WorkerOpeningBalanceBean> workerOpeningBalanceBeans = new LinkedList<WorkerOpeningBalanceBean>();
		Iterator<WorkerOpeningBalance> workerOpeningBalanceIterator = workerOpeningBalances.iterator();
		while (workerOpeningBalanceIterator.hasNext()) {
			WorkerOpeningBalance workerOpeningBalance = workerOpeningBalanceIterator.next();
			WorkerOpeningBalanceBean workerOpeningBalanceBean = new WorkerOpeningBalanceBean();
			workerOpeningBalanceBean.setCurrentBalance(workerOpeningBalance.getCurrentBalance());
			workerOpeningBalanceBean.setWorkerId(workerOpeningBalance.getWorkerId());
			workerOpeningBalanceBean.setWorkerOpeningBalanceId(workerOpeningBalance.getWorkerOpeningBalanceId());
			workerOpeningBalanceBean.setFinancialYear(workerOpeningBalance.getFinancialYear());
			workerOpeningBalanceBean.setOpeningBalance(workerOpeningBalance.getOpeningBalance());
			workerOpeningBalanceBeans.add(workerOpeningBalanceBean);
		}
		return workerOpeningBalanceBeans;
	}

	public static void updateCob(WorkerOpeningBalanceService cobService, double savedAmount, double currentAmount, long workerId, String finYear, String type) {
		double balance=0;
		if(savedAmount!=0)
		{
			balance=currentAmount-savedAmount;
		}
		else
		{
			balance=currentAmount;
		}
		WorkerOpeningBalance cob=cobService.getWorkerOpeningBalance(finYear,workerId);
		if(!ERPConstants.SALES_INVOICE.equals(type))
		{
			balance=-balance;
		}
		cob.setCurrentBalance(cob.getCurrentBalance()+balance);
		cobService.addWorkerOpeningBalance(cob);
	}

}
