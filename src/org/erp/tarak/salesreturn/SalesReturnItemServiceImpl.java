package org.erp.tarak.salesreturn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service("salesReturnItemService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SalesReturnItemServiceImpl implements SalesReturnItemService {

	@Autowired
	private SalesReturnItemDao salesReturnItemDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addSalesReturnItem(SalesReturnItem salesReturnItem) {
		salesReturnItemDao.addSalesReturnItem(salesReturnItem);
	}
	
	public List<SalesReturnItem> listSalesReturnItems() {
		return salesReturnItemDao.listSalesReturnItems();
	}

	public SalesReturnItem getSalesReturnItem(long salesReturnItemId) {
		return salesReturnItemDao.getSalesReturnItem(salesReturnItemId);
	}
	
	public void deleteSalesReturnItem(SalesReturnItem salesReturnItem) {
		salesReturnItemDao.deleteSalesReturnItem(salesReturnItem);
	}

	@Override
	public void deleteSalesReturnItems(List<SalesReturnItem> pois) {
		// TODO Auto-generated method stub
		salesReturnItemDao.deleteSalesReturnItems(pois);
	}

}
