package org.erp.tarak.shipper;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.erp.tarak.address.Address;

@Entity
@Table(name = "Shipper")
public class Shipper implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;

	@Id
	@SequenceGenerator(name="my_seq", sequenceName="shipperSequence",initialValue=1,allocationSize=1)
	@GeneratedValue(strategy = GenerationType.AUTO ,generator="my_seq")
	private long shipperId;
	private String shipperType;
	@Column(name = "Shipper_Name")
	private String shipperName;
	@Column(name = "Company_Name")
	private String companyName;
	@Column(name = "Company_Branch")
	private String companyBranch;
	@Column(name = "Company_Telephone1")
	private long companyTelephone1;
	@Column(name = "Company_Telephone2")
	private long companyTelephone2;
	@Column(name = "Company_Mobile1")
	private long companyMobile1;
	@Column(name = "Company_Mobile2")
	private long companyMobile2;
	@Column(name = "Company_Email")
	private String companyEmail;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Address_Id")
	private Address companyAddress = new Address();
	private double openingBalance;
	
	public long getShipperId() {
		return shipperId;
	}

	public void setShipperId(long shipperId) {
		this.shipperId = shipperId;
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

	public String getCompanyEmail() {
		return companyEmail;
	}

	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}

	public Address getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(Address companyAddress) {
		this.companyAddress = companyAddress;
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
