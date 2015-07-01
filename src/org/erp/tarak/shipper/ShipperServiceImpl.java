package org.erp.tarak.shipper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service("shipperService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ShipperServiceImpl implements ShipperService {

	@Autowired
	private ShipperDao shipperDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addShipper(Shipper shipper) {
		shipperDao.addShipper(shipper);
	}
	
	public List<Shipper> listShippers() {
		return shipperDao.listShippers();
	}

	public Shipper getShipper(long shipperId) {
		return shipperDao.getShipper(shipperId);
	}
	
	public void deleteShipper(Shipper shipper) {
		shipperDao.deleteShipper(shipper);
	}

	@Override
	public List<Shipper> listShippersbyCompanyName(String companyName) {
		return shipperDao.listShippersbyCompanyName(companyName);
	}

	@Override
	public List<Shipper> listShippersbyCompanyNameRegex(String search) {
		return shipperDao.listShippersbyCompanyNameRegex(search);
	}

}
