package org.erp.tarak.otherPayment;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.erp.tarak.worker.Worker;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "OtherPayment")
public class OtherPayment implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;

	@Id
	@GenericGenerator(name = "otherPaymentId", strategy = "org.erp.tarak.otherPayment.OtherPaymentIdGenerator")
	@GeneratedValue(generator = "otherPaymentId")
	private long otherPaymentId;
	private String finYear;
	@Temporal(value = TemporalType.DATE)
	@Column(name = "OP_DATE")
	private Date otherPaymentDate;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Worker_Id")
	private Worker workerId;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SELECT)
	@JoinTable(name = "OP_OPITEMS", inverseJoinColumns = {
			@JoinColumn(name = "OPITEMS_srNo", referencedColumnName = "srNo"),
			@JoinColumn(name = "OPITEMS_spId", referencedColumnName = "otherPaymentId"),
			@JoinColumn(name = "OPITEMS_Financial_Year", referencedColumnName = "Financial_Year") }, joinColumns = { @JoinColumn(name = "OP_otherPaymentId", referencedColumnName = "otherPaymentId") }

	)
	private List<OtherPaymentItem> otherPaymentItems;

	private double totalPaid;
	
	private String paymentMode;
	
	private String paymentReference;
	
	private double advance;
	
	private String payTo;
	
	public long getOtherPaymentId() {
		return otherPaymentId;
	}

	public void setOtherPaymentId(long otherPaymentId) {
		this.otherPaymentId = otherPaymentId;
	}

	public String getFinYear() {
		return finYear;
	}

	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}

	public double getTotalCost() {
		return totalPaid;
	}

	public void setTotalCost(double totalCost) {
		this.totalPaid = totalCost;
	}

	public List<OtherPaymentItem> getOtherPaymentItems() {
		return otherPaymentItems;
	}

	public void setOtherPaymentItems(List<OtherPaymentItem> otherPaymentItems) {
		this.otherPaymentItems = otherPaymentItems;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getOtherPaymentDate() {
		return otherPaymentDate;
	}

	public void setOtherPaymentDate(Date otherPaymentDate) {
		this.otherPaymentDate = otherPaymentDate;
	}

	public Worker getWorkerId() {
		return workerId;
	}

	public void setWorkerId(Worker workerId) {
		this.workerId = workerId;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getPaymentReference() {
		return paymentReference;
	}

	public void setPaymentReference(String paymentReference) {
		this.paymentReference = paymentReference;
	}

	public double getAdvance() {
		return advance;
	}

	public void setAdvance(double advance) {
		this.advance = advance;
	}

	public String getPayTo() {
		return payTo;
	}

	public void setPayTo(String payTo) {
		this.payTo = payTo;
	}

}
