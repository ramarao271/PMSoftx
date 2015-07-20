package org.erp.tarak.salesorder;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service("salesOrderItemService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SalesOrderItemServiceImpl implements SalesOrderItemService {

	@Autowired
	private SalesOrderItemDao salesOrderItemDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addSalesOrderItem(SalesOrderItem salesOrderItem) {
		salesOrderItemDao.addSalesOrderItem(salesOrderItem);
	}
	
	public List<SalesOrderItem> listSalesOrderItems() {
		return salesOrderItemDao.listSalesOrderItems();
	}

	public SalesOrderItem getSalesOrderItem(long salesOrderItemId) {
		return salesOrderItemDao.getSalesOrderItem(salesOrderItemId);
	}
	
	public void deleteSalesOrderItem(SalesOrderItem salesOrderItem) {
		salesOrderItemDao.deleteSalesOrderItem(salesOrderItem);
	}

	@Override
	public void deleteSalesOrderItems(List<SalesOrderItem> pois) {
		// TODO Auto-generated method stub
		salesOrderItemDao.deleteSalesOrderItems(pois);
	}

	@Override
	public List<Object[]> listLostSalesReportByCategoryWise(String finYear,
			int type, String fromDate,String toDate) {
		return salesOrderItemDao.listLostSalesReportByCategoryWise(finYear,type,fromDate,toDate);
	}

	@Override
	public List<Object[]> listLostSalesReportByCategoryWise(String finYear,
			int type, String date) {
		return salesOrderItemDao.listLostSalesReportByCategoryWise(finYear,type,date);
	}

	@Override
	public List<Object[]> listLostSalesReportByVariantWise(String finYear,
			int type, String fromDate, String toDate) {
		return salesOrderItemDao.listLostSalesReportByVariantWise(finYear,type,fromDate,toDate);
	}

	@Override
	public List<Object[]> listLostSalesReportByVariantWise(String finYear,
			int type, String date) {
		return salesOrderItemDao.listLostSalesReportByVariantWise(finYear,type,date);
	}

}
