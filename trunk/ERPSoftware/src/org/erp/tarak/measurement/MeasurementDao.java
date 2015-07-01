package org.erp.tarak.measurement;

import java.util.List;


public interface MeasurementDao {
		
		public void addMeasurement(Measurement measurement);

		public List<Measurement> listMeasurements();
		
		public Measurement getMeasurement(long measurementId);
		
		public void deleteMeasurement(Measurement measurement);

		public Measurement getMeasurementByName(String measurementName);
	}
