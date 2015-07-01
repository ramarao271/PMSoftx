package org.erp.tarak.balanceSheet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service("balanceSheetItemService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class BalanceSheetItemServiceImpl implements BalanceSheetItemService {

	@Autowired
	private BalanceSheetItemDao balanceSheetItemDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addBalanceSheetItem(BalanceSheetItem balanceSheetItem) {
		balanceSheetItemDao.addBalanceSheetItem(balanceSheetItem);
	}
	
	public List<BalanceSheetItem> listBalanceSheetItems() {
		return balanceSheetItemDao.listBalanceSheetItems();
	}

	public BalanceSheetItem getBalanceSheetItem(long balanceSheetItemId) {
		return balanceSheetItemDao.getBalanceSheetItem(balanceSheetItemId);
	}
	
	public void deleteBalanceSheetItem(BalanceSheetItem balanceSheetItem) {
		balanceSheetItemDao.deleteBalanceSheetItem(balanceSheetItem);
	}

	@Override
	public void deleteBalanceSheetItems(List<BalanceSheetItem> pois) {
		// TODO Auto-generated method stub
		balanceSheetItemDao.deleteBalanceSheetItems(pois);
	}

}
