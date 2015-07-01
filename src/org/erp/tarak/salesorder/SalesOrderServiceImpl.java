package org.erp.tarak.salesorder;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service("salesOrderService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SalesOrderServiceImpl implements SalesOrderService {

	@Autowired
	private SalesOrderDao salesOrderDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addSalesOrder(SalesOrder salesOrder) {
		salesOrderDao.addSalesOrder(salesOrder);
	}
	
	public List<SalesOrder> listSalesOrders(String finYear) {
		return salesOrderDao.listSalesOrders(finYear);
	}

	public SalesOrder getSalesOrder(long salesOrderId,String finYear) {
		return salesOrderDao.getSalesOrder(salesOrderId,finYear);
	}
	
	public void deleteSalesOrder(SalesOrder salesOrder) {
		salesOrderDao.deleteSalesOrder(salesOrder);
	}

	@Override
	public List<SalesOrder> listPendingSalesOrders(String finYear) {
		return salesOrderDao.listPendingSalesOrders(finYear);
		}

	@Override
	public List<SalesOrder> listProcessedSalesOrders(String finYear) {
		return salesOrderDao.listProcessedSalesOrders(finYear);
	}

}
