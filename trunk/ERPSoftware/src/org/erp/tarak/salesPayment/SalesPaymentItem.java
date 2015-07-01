package org.erp.tarak.salesPayment;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;


@Entity
@Table(name = "SalesPaymentItem")
public class SalesPaymentItem implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;
	
	@Id
/*	@SequenceGenerator(name="my_seq", sequenceName="spItemsSequence",initialValue=1,allocationSize=1)
	@GeneratedValue(strategy = GenerationType.AUTO ,generator="my_seq")
*/	private int srNo;
	
	@Id
	@GenericGenerator(name = "salesPaymentId", strategy = "org.erp.tarak.salesPayment.SalesPaymentIdGenerator")
	@GeneratedValue(generator = "salesPaymentId")
	private long salesPaymentId;
	
	@Id
	@Column(name="Financial_Year")
	private String finYear;
	
	private long salesInvoiceId;
	
	@Column(name="Amount_Paid")
	private double paid;
	
	@Type(type="boolean")
	private boolean processed;
	
	public int getSrNo() {
		return srNo;
	}
	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}
	public String getFinYear() {
		return finYear;
	}
	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}
	public long getSalesPaymentId() {
		return salesPaymentId;
	}
	public void setSalesPaymentId(long salesPaymentId) {
		this.salesPaymentId = salesPaymentId;
	}
	public double getPaid() {
		return paid;
	}
	public void setPaid(double paid) {
		this.paid = paid;
	}
	public long getSalesInvoiceId() {
		return salesInvoiceId;
	}
	public void setSalesInvoiceId(long salesInvoiceId) {
		this.salesInvoiceId = salesInvoiceId;
	}
	public boolean isProcessed() {
		return processed;
	}
	public void setProcessed(boolean processed) {
		this.processed = processed;
	}


}
