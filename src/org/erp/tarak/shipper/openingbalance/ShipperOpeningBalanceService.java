package org.erp.tarak.shipper.openingbalance;

import java.util.List;


public interface ShipperOpeningBalanceService {
	public void addShipperOpeningBalance(ShipperOpeningBalance shipperOpeningBalance);

	public void addShipperOpeningBalances(List<ShipperOpeningBalance> shipperOpeningBalances);
	
	public List<ShipperOpeningBalance> listShipperOpeningBalances();
	
	public ShipperOpeningBalance getShipperOpeningBalance(int shipperOpeningBalanceId);
	
	public void deleteShipperOpeningBalance(ShipperOpeningBalance shipperOpeningBalance);
	
	public void deleteShipperOpeningBalances(List<ShipperOpeningBalance> shipperOpeningBalances);

	public ShipperOpeningBalance getShipperOpeningBalance(String finYear,
			long shipperId);

}
