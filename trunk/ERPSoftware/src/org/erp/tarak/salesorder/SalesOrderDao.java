package org.erp.tarak.salesorder;

import java.util.List;


public interface SalesOrderDao {
	
	public void addSalesOrder(SalesOrder salesOrder);

	public List<SalesOrder> listSalesOrders(String finYear);
	
	public SalesOrder getSalesOrder(long salesOrderId,String finYear);
	
	public void deleteSalesOrder(SalesOrder salesOrder);

	public List<SalesOrder> listPendingSalesOrders(String finYear);

	public List<SalesOrder> listProcessedSalesOrders(String finYear);
}
