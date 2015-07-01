package org.erp.tarak.measurement;

import org.hibernate.validator.constraints.NotEmpty;

public class MeasurementBean {
	private long measurementId;
	@NotEmpty(message="Measurement cannot be empty")
	private String measurementName;

	public String getMeasurementName() {
		return measurementName;
	}

	public void setMeasurementName(String measurementName) {
		this.measurementName = measurementName;
	}

	public long getMeasurementId() {
		return measurementId;
	}

	public void setMeasurementId(long measurementId) {
		this.measurementId = measurementId;
	}
}
