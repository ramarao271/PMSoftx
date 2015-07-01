package org.erp.tarak.salesreturn;

import java.util.List;


public interface SalesReturnItemDao {
	
	public void addSalesReturnItem(SalesReturnItem salesReturnItem);

	public List<SalesReturnItem> listSalesReturnItems();
	
	public SalesReturnItem getSalesReturnItem(long salesReturnItemId);
	
	public void deleteSalesReturnItem(SalesReturnItem salesReturnItem);

	public void deleteSalesReturnItems(List<SalesReturnItem> pois);
}
