package org.erp.tarak.shipper;

import org.erp.tarak.address.AddressBean;



public class ShipperBean {

	private long shipperId;
	private String shipperType;
	private String shipperName;
	private String companyName;
	private String companyBranch;
	private long companyTelephone1;
	private long companyTelephone2;
	private long companyMobile1;
	private long companyMobile2;
	private String companyEmail;
	private double openingBalance;
	private AddressBean companyAddressBean;
	public ShipperBean() {
		super();
	}
	public String getShipperName() {
		return shipperName;
	}
	public void setShipperName(String shipperName) {
		this.shipperName = shipperName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyBranch() {
		return companyBranch;
	}
	public void setCompanyBranch(String companyBranch) {
		this.companyBranch = companyBranch;
	}
	
	public void setCompanyMobile2(Integer companyMobile2) {
		this.companyMobile2 = companyMobile2;
	}
	public String getCompanyEmail() {
		return companyEmail;
	}
	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}
	public AddressBean getCompanyAddressBean() {
		return companyAddressBean;
	}
	public void setCompanyAddressBean(AddressBean companyAddressBean) {
		this.companyAddressBean = companyAddressBean;
	}
	public long getShipperId() {
		return shipperId;
	}
	public void setShipperId(long shipperId) {
		this.shipperId = shipperId;
	}
	public long getCompanyTelephone1() {
		return companyTelephone1;
	}
	public void setCompanyTelephone1(long companyTelephone1) {
		this.companyTelephone1 = companyTelephone1;
	}
	public long getCompanyTelephone2() {
		return companyTelephone2;
	}
	public void setCompanyTelephone2(long companyTelephone2) {
		this.companyTelephone2 = companyTelephone2;
	}
	public long getCompanyMobile1() {
		return companyMobile1;
	}
	public void setCompanyMobile1(long companyMobile1) {
		this.companyMobile1 = companyMobile1;
	}
	public long getCompanyMobile2() {
		return companyMobile2;
	}
	public void setCompanyMobile2(long companyMobile2) {
		this.companyMobile2 = companyMobile2;
	}
	public String getShipperType() {
		return shipperType;
	}
	public void setShipperType(String shipperType) {
		this.shipperType = shipperType;
	}
	public double getOpeningBalance() {
		return openingBalance;
	}
	public void setOpeningBalance(double openingBalance) {
		this.openingBalance = openingBalance;
	}
}
