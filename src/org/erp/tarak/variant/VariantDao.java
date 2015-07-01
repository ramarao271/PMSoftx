package org.erp.tarak.variant;

import java.util.List;


public interface VariantDao {
	
	public void addVariant(Variant product);

	public List<Variant> listVariants();
	
	public Variant getVariant(long variantId);
	
	public void deleteVariant(Variant variant);
}
