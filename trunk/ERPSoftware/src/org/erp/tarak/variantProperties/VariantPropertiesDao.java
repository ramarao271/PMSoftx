package org.erp.tarak.variantProperties;

import java.util.List;


public interface VariantPropertiesDao {
	
	public void addVariantProperties(VariantProperties product);

	public List<VariantProperties> listVariantPropertiess();
	
	public VariantProperties getVariantProperties(long variantPropertiesId);
	
	public void deleteVariantProperties(VariantProperties variantProperties);

	public VariantProperties getVariantPropertiesByNameNType(
			String variantPropertiesName, String variantPropertiesType);

	public List<VariantProperties> getVariantPropertiesByType(String variantPropertiesType);
}

