package org.erp.tarak.shipper.openingbalance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service("shipperOpeningBalanceService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ShipperOpeningBalanceServiceImpl implements ShipperOpeningBalanceService {

	@Autowired
	private ShipperOpeningBalanceDao shipperOpeningBalanceDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addShipperOpeningBalance(ShipperOpeningBalance shipperOpeningBalance) {
		shipperOpeningBalanceDao.addShipperOpeningBalance(shipperOpeningBalance);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addShipperOpeningBalances(List<ShipperOpeningBalance> shipperOpeningBalances) {
		shipperOpeningBalanceDao.addShipperOpeningBalances(shipperOpeningBalances);
	}
	
	public List<ShipperOpeningBalance> listShipperOpeningBalances() {
		return shipperOpeningBalanceDao.listShipperOpeningBalances();
	}

	public ShipperOpeningBalance getShipperOpeningBalance(int shipperOpeningBalanceId) {
		return shipperOpeningBalanceDao.getShipperOpeningBalance(shipperOpeningBalanceId);
	}
	
	public void deleteShipperOpeningBalance(ShipperOpeningBalance shipperOpeningBalance) {
		shipperOpeningBalanceDao.deleteShipperOpeningBalance(shipperOpeningBalance);
	}
	public void deleteShipperOpeningBalances(List<ShipperOpeningBalance> shipperOpeningBalances) {
		shipperOpeningBalanceDao.deleteShipperOpeningBalances(shipperOpeningBalances);
	}

	@Override
	public ShipperOpeningBalance getShipperOpeningBalance(String finYear,
			long shipperId) {
		return shipperOpeningBalanceDao.getShipperOpeningBalance(finYear,shipperId);
	}

}
