package org.erp.tarak.otherPayment;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;


@Entity
@Table(name = "OtherPaymentItem")
public class OtherPaymentItem implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;
	
	@Id
/*	@SequenceGenerator(name="my_seq", sequenceName="spItemsSequence",initialValue=1,allocationSize=1)
	@GeneratedValue(strategy = GenerationType.AUTO ,generator="my_seq")
*/	private int srNo;
	
	@Id
	@GenericGenerator(name = "otherPaymentId", strategy = "org.erp.tarak.otherPayment.OtherPaymentIdGenerator")
	@GeneratedValue(generator = "otherPaymentId")
	private long otherPaymentId;
	
	@Id
	@Column(name="Financial_Year")
	private String finYear;
	
	private long productionInvoiceId;
	
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
	public long getOtherPaymentId() {
		return otherPaymentId;
	}
	public void setOtherPaymentId(long otherPaymentId) {
		this.otherPaymentId = otherPaymentId;
	}
	public double getPaid() {
		return paid;
	}
	public void setPaid(double paid) {
		this.paid = paid;
	}
	public long getProductionInvoiceId() {
		return productionInvoiceId;
	}
	public void setProductionInvoiceId(long productionInvoiceId) {
		this.productionInvoiceId = productionInvoiceId;
	}
	public boolean isProcessed() {
		return processed;
	}
	public void setProcessed(boolean processed) {
		this.processed = processed;
	}


}
