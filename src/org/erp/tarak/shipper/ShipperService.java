package org.erp.tarak.shipper;

import java.util.List;


public interface ShipperService {

	public void addShipper(Shipper shipper);

	public List<Shipper> listShippers();

	public Shipper getShipper(long shipperId);

	public void deleteShipper(Shipper shipper);
	
	public List<Shipper> listShippersbyCompanyName(String companyName);

	public List<Shipper> listShippersbyCompanyNameRegex(String search);
}
