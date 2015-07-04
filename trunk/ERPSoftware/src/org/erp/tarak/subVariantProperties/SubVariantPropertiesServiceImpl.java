package org.erp.tarak.subVariantProperties;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service("subVariantPropertiesService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SubVariantPropertiesServiceImpl implements SubVariantPropertiesService {

	@Autowired
	private SubVariantPropertiesDao subVariantPropertiesDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addSubVariantProperties(SubVariantProperties subVariantProperties) {
		subVariantPropertiesDao.addSubVariantProperties(subVariantProperties);
	}
	
	public List<SubVariantProperties> listSubVariantPropertiess() {
		return subVariantPropertiesDao.listSubVariantPropertiess();
	}

	public SubVariantProperties getSubVariantProperties(long subVariantPropertiesId) {
		return subVariantPropertiesDao.getSubVariantProperties(subVariantPropertiesId);
	}
	
	public void deleteSubVariantProperties(SubVariantProperties subVariantProperties) {
		subVariantPropertiesDao.deleteSubVariantProperties(subVariantProperties);
	}

	@Override
	public SubVariantProperties getSubVariantPropertiesByNameNType(
			String subVariantPropertiesName) {
		
		return subVariantPropertiesDao.getSubVariantPropertiesByNameNType(subVariantPropertiesName);
	}

	@Override
	public List<SubVariantProperties> getSubVariantPropertiesByType(String subVariantPropertiesType) {
		
		return subVariantPropertiesDao.getSubVariantPropertiesByType(subVariantPropertiesType);
	}

}
