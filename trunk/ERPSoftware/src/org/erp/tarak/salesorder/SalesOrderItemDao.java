package org.erp.tarak.salesorder;

import java.util.List;


public interface SalesOrderItemDao {
	
	public void addSalesOrderItem(SalesOrderItem salesOrderItem);

	public List<SalesOrderItem> listSalesOrderItems();
	
	public SalesOrderItem getSalesOrderItem(long salesOrderItemId);
	
	public void deleteSalesOrderItem(SalesOrderItem salesOrderItem);

	public void deleteSalesOrderItems(List<SalesOrderItem> pois);
}
