package org.erp.tarak.balanceSheet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service("balanceSheetService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class BalanceSheetServiceImpl implements BalanceSheetService {

	@Autowired
	private BalanceSheetDao balanceSheetDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addBalanceSheet(BalanceSheet balanceSheet) {
		balanceSheetDao.addBalanceSheet(balanceSheet);
	}
	
	public List<BalanceSheet> listBalanceSheets() {
		return balanceSheetDao.listBalanceSheets();
	}

	public BalanceSheet getBalanceSheet(long balanceSheetId) {
		return balanceSheetDao.getBalanceSheet(balanceSheetId);
	}
	
	public void deleteBalanceSheet(BalanceSheet balanceSheet) {
		balanceSheetDao.deleteBalanceSheet(balanceSheet);
	}

	@Override
	public List<BalanceSheet> listPendingBalanceSheets() {
		return balanceSheetDao.listPendingBalanceSheets();
		}

	@Override
	public List<BalanceSheet> listProcessedBalanceSheets() {
		return balanceSheetDao.listProcessedBalanceSheets();
	}

}
