package org.erp.tarak.shipper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.erp.tarak.address.Address;
import org.erp.tarak.address.AddressBean;
import org.erp.tarak.address.AddressUtilities;

public class ShipperUtilities {

	public static Shipper getShipperModel(ShipperService shipperService,
			long shipperId) {
		Shipper shipper = shipperService.getShipper(shipperId);
		return shipper;
	}

	public static Shipper prepareShipperModel(ShipperBean shipperBean) {
		Shipper shipper = new Shipper();
		shipper.setCompanyBranch(shipperBean.getCompanyBranch());
		shipper.setCompanyEmail(shipperBean.getCompanyEmail());
		shipper.setCompanyMobile1(shipperBean.getCompanyMobile1());
		shipper.setCompanyMobile2(shipperBean.getCompanyMobile2());
		shipper.setCompanyName(shipperBean.getCompanyName());
		shipper.setCompanyTelephone1(shipperBean.getCompanyTelephone1());
		shipper.setCompanyTelephone2(shipperBean.getCompanyTelephone2());
		shipper.setShipperId(shipperBean.getShipperId());
		shipper.setShipperName(shipperBean.getShipperName());
		shipper.setOpeningBalance(shipperBean.getOpeningBalance());
		if (shipperBean.getCompanyAddressBean() != null) {
			Address address = AddressUtilities.populateAddress(shipperBean
					.getCompanyAddressBean());
			shipper.setCompanyAddress(address);
		}
		return shipper;
	}

	public static List<ShipperBean> prepareListofShipperBean(
			List<Shipper> shippers) {
		List<ShipperBean> beans = null;
		if (shippers != null && !shippers.isEmpty()) {
			beans = new ArrayList<ShipperBean>();
			ShipperBean bean = null;
			for (Shipper shipper : shippers) {
				bean = new ShipperBean();
				bean.setCompanyBranch(shipper.getCompanyBranch());
				bean.setCompanyEmail(shipper.getCompanyEmail());
				bean.setCompanyMobile1(shipper.getCompanyMobile1());
				bean.setCompanyMobile2(shipper.getCompanyMobile2());
				bean.setCompanyName(shipper.getCompanyName());
				bean.setCompanyTelephone1(shipper.getCompanyTelephone1());
				bean.setCompanyTelephone2(shipper.getCompanyTelephone2());
				bean.setShipperId(shipper.getShipperId());
				bean.setShipperName(shipper.getShipperName());
				AddressBean addressBean = AddressUtilities
						.prepareAddressBean(shipper.getCompanyAddress());
				bean.setCompanyAddressBean(addressBean);
				bean.setOpeningBalance(shipper.getOpeningBalance());
				beans.add(bean);
			}
		}
		return beans;
	}

	public static ShipperBean prepareShipperBean(long shipperId,
			ShipperService shipperService) {
		ShipperBean bean = new ShipperBean();
		Shipper shipper = ShipperUtilities.getShipperModel(shipperService,
				shipperId);

		bean.setCompanyBranch(shipper.getCompanyBranch());
		bean.setCompanyEmail(shipper.getCompanyEmail());
		bean.setCompanyMobile1(shipper.getCompanyMobile1());
		bean.setCompanyMobile2(shipper.getCompanyMobile2());
		bean.setCompanyName(shipper.getCompanyName());
		bean.setCompanyTelephone1(shipper.getCompanyTelephone1());
		bean.setCompanyTelephone2(shipper.getCompanyTelephone2());
		AddressBean addressBean = AddressUtilities.prepareAddressBean(shipper
				.getCompanyAddress());
		bean.setCompanyAddressBean(addressBean);
		bean.setShipperId(shipper.getShipperId());
		bean.setShipperName(shipper.getShipperName());
		bean.setOpeningBalance(shipper.getOpeningBalance());
		return bean;
	}

	public static ShipperBean prepareShipperBean(Shipper shipper) {
		ShipperBean bean = new ShipperBean();
		if(shipper!=null)
		{
			bean.setCompanyBranch(shipper.getCompanyBranch());
			bean.setCompanyEmail(shipper.getCompanyEmail());
			bean.setCompanyMobile1(shipper.getCompanyMobile1());
			bean.setCompanyMobile2(shipper.getCompanyMobile2());
			bean.setCompanyName(shipper.getCompanyName());
			bean.setCompanyTelephone1(shipper.getCompanyTelephone1());
			bean.setCompanyTelephone2(shipper.getCompanyTelephone2());
			AddressBean addressBean = AddressUtilities.prepareAddressBean(shipper
				.getCompanyAddress());
			bean.setCompanyAddressBean(addressBean);
			bean.setShipperId(shipper.getShipperId());
			bean.setShipperName(shipper.getShipperName());
			bean.setOpeningBalance(shipper.getOpeningBalance());
		}
		return bean;
	}

	public static List<ShipperBean> prepareListofShipperBeans(
			List<Shipper> shippers) {
		List<ShipperBean> beans = null;
		if (shippers != null && !shippers.isEmpty()) {
			beans = new LinkedList<ShipperBean>();
			ShipperBean bean = null;
			for (Shipper shipper : shippers) {
				bean = new ShipperBean();
				AddressBean addressBean = AddressUtilities
						.prepareAddressBean(shipper.getCompanyAddress());
				bean.setCompanyAddressBean(addressBean);
				bean.setCompanyBranch(shipper.getCompanyBranch());
				bean.setCompanyEmail(shipper.getCompanyEmail());
				bean.setCompanyMobile1(shipper.getCompanyMobile1());
				bean.setCompanyMobile2(shipper.getCompanyMobile2());
				bean.setCompanyName(shipper.getCompanyName());
				bean.setCompanyTelephone1(shipper.getCompanyTelephone1());
				bean.setCompanyTelephone2(shipper.getCompanyTelephone2());
				bean.setShipperId(shipper.getShipperId());
				bean.setShipperName(shipper.getShipperName());
				bean.setOpeningBalance(shipper.getOpeningBalance());
				beans.add(bean);
			}
		}
		return beans;
	}

}
