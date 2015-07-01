package org.erp.tarak.variant;

import java.util.List;


public interface VariantService {
	
	public void addVariant(Variant variant);

	public List<Variant> listVariants();
	
	public Variant getVariant(long variantId);
	
	public void deleteVariant(Variant variant);
}
