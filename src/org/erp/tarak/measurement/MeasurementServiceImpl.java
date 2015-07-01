package org.erp.tarak.measurement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service("measurementService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class MeasurementServiceImpl implements MeasurementService {

	@Autowired
	private MeasurementDao measurementDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addMeasurement(Measurement measurement) {
		measurementDao.addMeasurement(measurement);
	}
	
	public List<Measurement> listMeasurements() {
		return measurementDao.listMeasurements();
	}

	public Measurement getMeasurement(long measurementId) {
		return measurementDao.getMeasurement(measurementId);
	}
	
	public void deleteMeasurement(Measurement measurement) {
		measurementDao.deleteMeasurement(measurement);
	}

	@Override
	public Measurement getMeasurementByName(String measurementName) {
		return measurementDao.getMeasurementByName(measurementName);
	}

}
