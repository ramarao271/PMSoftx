package org.erp.tarak.salesorder;

import java.util.List;


public interface SalesOrderItemDao {
	
	public void addSalesOrderItem(SalesOrderItem salesOrderItem);

	public List<SalesOrderItem> listSalesOrderItems();
	
	public SalesOrderItem getSalesOrderItem(long salesOrderItemId);
	
	public void deleteSalesOrderItem(SalesOrderItem salesOrderItem);

	public void deleteSalesOrderItems(List<SalesOrderItem> pois);

	public List<Object[]> listLostSalesReportByCategoryWise(String finYear,
			int type, String fromDate,String toDate);

	public List<Object[]> listLostSalesReportByCategoryWise(String finYear,
			int type, String date);

	public List<Object[]> listLostSalesReportByVariantWise(String finYear,
			int type, String fromDate, String toDate);

	public List<Object[]> listLostSalesReportByVariantWise(String finYear,
			int type, String date);
}
