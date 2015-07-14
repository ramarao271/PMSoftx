package org.erp.tarak.shipper.openingbalance;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.erp.tarak.library.ERPConstants;

public class ShipperOpeningBalanceUtilities {

	public static List<ShipperOpeningBalance> saveShipperOpeningBalances(
			ShipperOpeningBalanceService shipperOpeningBalanceService,
			List<ShipperOpeningBalance> shipperOpeningBalances) {
		shipperOpeningBalanceService.addShipperOpeningBalances(shipperOpeningBalances);
		return shipperOpeningBalances;
	}

	public static List<ShipperOpeningBalance> populateShipperOpeningBalances(
			List<ShipperOpeningBalanceBean> shipperOpeningBalanceBeans) {
		List<ShipperOpeningBalance> shipperOpeningBalances = new LinkedList<ShipperOpeningBalance>();
		if (shipperOpeningBalanceBeans != null) {
			for (ShipperOpeningBalanceBean shipperOpeningBalanceBean : shipperOpeningBalanceBeans) {
				if (shipperOpeningBalanceBean.getShipperOpeningBalanceId() != 0
						&& !"".equals(shipperOpeningBalanceBean.getShipperOpeningBalanceId())) {
					ShipperOpeningBalance shipperOpeningBalance = new ShipperOpeningBalance();
					shipperOpeningBalance.setCurrentBalance(shipperOpeningBalanceBean.getCurrentBalance());
					shipperOpeningBalance.setShipperId(shipperOpeningBalanceBean.getShipperId());
					shipperOpeningBalance.setShipperOpeningBalanceId(shipperOpeningBalanceBean.getShipperOpeningBalanceId());
					shipperOpeningBalance.setFinancialYear(shipperOpeningBalanceBean.getFinancialYear());
					shipperOpeningBalance.setOpeningBalance(shipperOpeningBalanceBean.getOpeningBalance());
					shipperOpeningBalances.add(shipperOpeningBalance);
				}
			}
		}
		return shipperOpeningBalances;
	}

	public static List<ShipperOpeningBalanceBean> prepareShipperOpeningBalanceBean(
			List<ShipperOpeningBalance> shipperOpeningBalances) {
		List<ShipperOpeningBalanceBean> shipperOpeningBalanceBeans = new LinkedList<ShipperOpeningBalanceBean>();
		Iterator<ShipperOpeningBalance> shipperOpeningBalanceIterator = shipperOpeningBalances.iterator();
		while (shipperOpeningBalanceIterator.hasNext()) {
			ShipperOpeningBalance shipperOpeningBalance = shipperOpeningBalanceIterator.next();
			ShipperOpeningBalanceBean shipperOpeningBalanceBean = new ShipperOpeningBalanceBean();
			shipperOpeningBalanceBean.setCurrentBalance(shipperOpeningBalance.getCurrentBalance());
			shipperOpeningBalanceBean.setShipperId(shipperOpeningBalance.getShipperId());
			shipperOpeningBalanceBean.setShipperOpeningBalanceId(shipperOpeningBalance.getShipperOpeningBalanceId());
			shipperOpeningBalanceBean.setFinancialYear(shipperOpeningBalance.getFinancialYear());
			shipperOpeningBalanceBean.setOpeningBalance(shipperOpeningBalance.getOpeningBalance());
			shipperOpeningBalanceBeans.add(shipperOpeningBalanceBean);
		}
		return shipperOpeningBalanceBeans;
	}

	public static void updateCob(ShipperOpeningBalanceService cobService, double savedAmount, double currentAmount, long shipperId, String finYear, String type) {
		double balance=0;
		if(savedAmount!=0)
		{
			balance=currentAmount-savedAmount;
		}
		else
		{
			balance=currentAmount;
		}
		ShipperOpeningBalance cob=cobService.getShipperOpeningBalance(finYear,shipperId);
		if(!ERPConstants.SALES_INVOICE.equals(type))
		{
			balance=-balance;
		}
		cob.setCurrentBalance(cob.getCurrentBalance()+balance);
		cobService.addShipperOpeningBalance(cob);
	}

}
