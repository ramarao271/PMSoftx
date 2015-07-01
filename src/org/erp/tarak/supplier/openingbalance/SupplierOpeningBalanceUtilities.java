package org.erp.tarak.supplier.openingbalance;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.erp.tarak.library.ERPConstants;

public class SupplierOpeningBalanceUtilities {

	public static List<SupplierOpeningBalance> saveSupplierOpeningBalances(
			SupplierOpeningBalanceService supplierOpeningBalanceService,
			List<SupplierOpeningBalance> supplierOpeningBalances) {
		supplierOpeningBalanceService.addSupplierOpeningBalances(supplierOpeningBalances);
		return supplierOpeningBalances;
	}

	public static List<SupplierOpeningBalance> populateSupplierOpeningBalances(
			List<SupplierOpeningBalanceBean> supplierOpeningBalanceBeans) {
		List<SupplierOpeningBalance> supplierOpeningBalances = new LinkedList<SupplierOpeningBalance>();
		if (supplierOpeningBalanceBeans != null) {
			for (SupplierOpeningBalanceBean supplierOpeningBalanceBean : supplierOpeningBalanceBeans) {
				if (supplierOpeningBalanceBean.getSupplierOpeningBalanceId() != 0
						&& !"".equals(supplierOpeningBalanceBean.getSupplierOpeningBalanceId())) {
					SupplierOpeningBalance supplierOpeningBalance = new SupplierOpeningBalance();
					supplierOpeningBalance.setCurrentBalance(supplierOpeningBalanceBean.getCurrentBalance());
					supplierOpeningBalance.setSupplierId(supplierOpeningBalanceBean.getSupplierId());
					supplierOpeningBalance.setSupplierOpeningBalanceId(supplierOpeningBalanceBean.getSupplierOpeningBalanceId());
					supplierOpeningBalance.setFinancialYear(supplierOpeningBalanceBean.getFinancialYear());
					supplierOpeningBalance.setOpeningBalance(supplierOpeningBalanceBean.getOpeningBalance());
					supplierOpeningBalances.add(supplierOpeningBalance);
				}
			}
		}
		return supplierOpeningBalances;
	}

	public static List<SupplierOpeningBalanceBean> prepareSupplierOpeningBalanceBean(
			List<SupplierOpeningBalance> supplierOpeningBalances) {
		List<SupplierOpeningBalanceBean> supplierOpeningBalanceBeans = new LinkedList<SupplierOpeningBalanceBean>();
		Iterator<SupplierOpeningBalance> supplierOpeningBalanceIterator = supplierOpeningBalances.iterator();
		while (supplierOpeningBalanceIterator.hasNext()) {
			SupplierOpeningBalance supplierOpeningBalance = supplierOpeningBalanceIterator.next();
			SupplierOpeningBalanceBean supplierOpeningBalanceBean = new SupplierOpeningBalanceBean();
			supplierOpeningBalanceBean.setCurrentBalance(supplierOpeningBalance.getCurrentBalance());
			supplierOpeningBalanceBean.setSupplierId(supplierOpeningBalance.getSupplierId());
			supplierOpeningBalanceBean.setSupplierOpeningBalanceId(supplierOpeningBalance.getSupplierOpeningBalanceId());
			supplierOpeningBalanceBean.setFinancialYear(supplierOpeningBalance.getFinancialYear());
			supplierOpeningBalanceBean.setOpeningBalance(supplierOpeningBalance.getOpeningBalance());
			supplierOpeningBalanceBeans.add(supplierOpeningBalanceBean);
		}
		return supplierOpeningBalanceBeans;
	}

	public static void updateCob(SupplierOpeningBalanceService cobService, double savedAmount, double currentAmount, long supplierId, String finYear, String type) {
		double balance=0;
		if(savedAmount!=0)
		{
			balance=currentAmount-savedAmount;
		}
		else
		{
			balance=currentAmount;
		}
		SupplierOpeningBalance cob=cobService.getSupplierOpeningBalance(finYear,supplierId);
		if(!ERPConstants.SALES_INVOICE.equals(type))
		{
			balance=-balance;
		}
		cob.setCurrentBalance(cob.getCurrentBalance()+balance);
		cobService.addSupplierOpeningBalance(cob);
	}

}
