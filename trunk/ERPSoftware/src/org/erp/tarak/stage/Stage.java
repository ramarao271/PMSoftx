package org.erp.tarak.stage;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="Stage")
public class Stage  implements Serializable{
	
	private static final long serialVersionUID = -723583058586873479L;
	
	@Id
	@SequenceGenerator(name="my_seq", sequenceName="stageSequence",initialValue=1,allocationSize=1)
	@GeneratedValue(strategy = GenerationType.AUTO ,generator="my_seq")
	private long stageId; 
	
	@Column(name="Stage_Name")
	private String stageName;

	@GenericGenerator(name = "stageCode", strategy = "org.erp.tarak.stage.StageCodeGenerator")
	@GeneratedValue(generator = "stageCode",strategy = GenerationType.AUTO)
	@Column(name="Stage_Code")
	private String stageCode;

	private double quantity;
	
	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

	public long getStageId() {
		return stageId;
	}

	public void setStageId(long stageId) {
		this.stageId = stageId;
	}

	public String getStageCode() {
		return stageCode;
	}

	public void setStageCode(String stageCode) {
		this.stageCode = stageCode;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
}
