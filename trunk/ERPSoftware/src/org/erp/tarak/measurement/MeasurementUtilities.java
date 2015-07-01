package org.erp.tarak.measurement;

import java.util.ArrayList;
import java.util.List;


public class MeasurementUtilities {
	
	public static List<MeasurementBean> prepareListofMeasurementBean(
			List<Measurement> listMeasurements) {

		List<MeasurementBean> beans = new ArrayList<MeasurementBean>();
		for (Measurement measurement : listMeasurements) {
			MeasurementBean measurementBean = new MeasurementBean();
			measurementBean.setMeasurementId(measurement.getMeasurementId());
			measurementBean
					.setMeasurementName(measurement.getMeasurementName());
			beans.add(measurementBean);
		}
		return beans;
	}

	public static Measurement prepareMeasurementModel(
			MeasurementBean measurementBean) {
		Measurement measurement = new Measurement();
		measurement.setMeasurementId(measurementBean.getMeasurementId());
		measurement.setMeasurementName(measurementBean.getMeasurementName());
		return measurement;
	}

	public static MeasurementBean prepareMeasurementBean(Measurement measurement) {
		MeasurementBean bean = new MeasurementBean();
		bean.setMeasurementId(measurement.getMeasurementId());
		bean.setMeasurementName(measurement.getMeasurementName());
		return bean;
	}

	public static boolean isMeasurementExists(
			MeasurementService measurementService, String measurementName) {
		Measurement measurment =measurementService.getMeasurementByName(measurementName);
		if(measurment!=null && measurment.getMeasurementId()>0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

}
