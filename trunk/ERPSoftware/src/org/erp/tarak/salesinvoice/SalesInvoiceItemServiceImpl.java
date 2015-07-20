package org.erp.tarak.salesinvoice;

import java.util.List;

import org.erp.tarak.category.CategoryReport;
import org.erp.tarak.variant.VariantReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service("salesInvoiceItemService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SalesInvoiceItemServiceImpl implements SalesInvoiceItemService {

	@Autowired
	private SalesInvoiceItemDao salesInvoiceItemDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addSalesInvoiceItem(SalesInvoiceItem salesInvoiceItem) {
		salesInvoiceItemDao.addSalesInvoiceItem(salesInvoiceItem);
	}
	
	public List<SalesInvoiceItem> listSalesInvoiceItems(String finYear) {
		return salesInvoiceItemDao.listSalesInvoiceItems(finYear);
	}

	public SalesInvoiceItem getSalesInvoiceItem(long salesInvoiceItemId,String finYear) {
		return salesInvoiceItemDao.getSalesInvoiceItem(salesInvoiceItemId,finYear);
	}
	
	public void deleteSalesInvoiceItem(SalesInvoiceItem salesInvoiceItem) {
		salesInvoiceItemDao.deleteSalesInvoiceItem(salesInvoiceItem);
	}

	@Override
	public void deleteSalesInvoiceItems(List<SalesInvoiceItem> pois) {
		// TODO Auto-generated method stub
		salesInvoiceItemDao.deleteSalesInvoiceItems(pois);
	}

	@Override
	public List<CategoryReport> getSalesReportByCategory(String finYear) {
		return salesInvoiceItemDao.getSalesReportByCategory(finYear);
	}

	@Override
	public List<Object[]> listSalesInvoiceItemsByCategory(long id,
			String finYear) {
		return salesInvoiceItemDao.listSalesInvoiceItemsByCategory(id,finYear);
	}

	@Override
	public List<Object[]> listFrequesntlyProductsByCustomer(long customerId,
			String finYear) {
		return salesInvoiceItemDao.listFrequesntlyProductsByCustomer(customerId,finYear);
	}

	@Override
	public List<VariantReport> getSalesReportByVariant(String finYear) {
		return salesInvoiceItemDao.getSalesReportByVariant(finYear);
	}

	@Override
	public List<Object[]> listSalesReportByCategoryWise(String finYear,int type,String date) {
		return salesInvoiceItemDao.listSalesReportByCategoryWise(finYear,type,date);
	}

	@Override
	public List<Object[]> listSalesReportByCategoryWise(String finYear,
			int date, String fromDate, String toDate) {
		return salesInvoiceItemDao.listSalesReportByCategoryWise(finYear,date,fromDate,toDate);
	}

	@Override
	public List<Object[]> getProfitReportByCategoryWise(String finYear,
			int type, String date) {
		return salesInvoiceItemDao.getProfitReportByCategoryWise(finYear,type, date);
	}

	@Override
	public List<Object[]> listProfitReportByCategoryWise(String finYear,
			int type, String fromDate, String toDate) {
		// TODO Auto-generated method stub
		return salesInvoiceItemDao.listProfitReportByCategoryWise(finYear,type,fromDate,toDate);
	}


}
