package org.erp.tarak.subVariantProperties;

import java.util.List;


public interface SubVariantPropertiesService {
	
	public void addSubVariantProperties(SubVariantProperties subVariantProperties);

	public List<SubVariantProperties> listSubVariantPropertiess();
	
	public SubVariantProperties getSubVariantProperties(long subVariantPropertiesId);
	
	public void deleteSubVariantProperties(SubVariantProperties subVariantProperties);

	public SubVariantProperties getSubVariantPropertiesByNameNType(
			String subVariantPropertiesName);

	public List<SubVariantProperties> getSubVariantPropertiesByType(String variantType);
}
