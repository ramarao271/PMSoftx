package org.erp.tarak.variantProperties;

import java.util.List;


public interface VariantPropertiesService {
	
	public void addVariantProperties(VariantProperties variantProperties);

	public List<VariantProperties> listVariantPropertiess();
	
	public VariantProperties getVariantProperties(long variantPropertiesId);
	
	public void deleteVariantProperties(VariantProperties variantProperties);

	public VariantProperties getVariantPropertiesByNameNType(
			String variantPropertiesName, String variantPropertiesType);

	public List<VariantProperties> getVariantPropertiesByType(String variantType);
}
