package org.erp.tarak.variantProperties;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service("variantPropertiesService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class VariantPropertiesServiceImpl implements VariantPropertiesService {

	@Autowired
	private VariantPropertiesDao variantPropertiesDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addVariantProperties(VariantProperties variantProperties) {
		variantPropertiesDao.addVariantProperties(variantProperties);
	}
	
	public List<VariantProperties> listVariantPropertiess() {
		return variantPropertiesDao.listVariantPropertiess();
	}

	public VariantProperties getVariantProperties(long variantPropertiesId) {
		return variantPropertiesDao.getVariantProperties(variantPropertiesId);
	}
	
	public void deleteVariantProperties(VariantProperties variantProperties) {
		variantPropertiesDao.deleteVariantProperties(variantProperties);
	}

	@Override
	public VariantProperties getVariantPropertiesByNameNType(
			String variantPropertiesName, String variantPropertiesType) {
		
		return variantPropertiesDao.getVariantPropertiesByNameNType(variantPropertiesName,variantPropertiesType);
	}

	@Override
	public List<VariantProperties> getVariantPropertiesByType(String variantPropertiesType) {
		
		return variantPropertiesDao.getVariantPropertiesByType(variantPropertiesType);
	}

}
