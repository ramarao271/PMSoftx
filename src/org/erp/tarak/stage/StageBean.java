package org.erp.tarak.stage;

import org.hibernate.validator.constraints.NotEmpty;

public class StageBean {
	private long stageId;
	@NotEmpty(message="Stage cannot be empty")
	private String stageName;
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
