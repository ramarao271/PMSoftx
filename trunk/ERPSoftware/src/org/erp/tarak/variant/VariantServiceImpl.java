package org.erp.tarak.variant;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service("variantService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class VariantServiceImpl implements VariantService {

	@Autowired
	private VariantDao variantDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addVariant(Variant variant) {
		variantDao.addVariant(variant);
	}
	
	public List<Variant> listVariants() {
		return variantDao.listVariants();
	}

	public Variant getVariant(long variantId) {
		return variantDao.getVariant(variantId);
	}
	
	public void deleteVariant(Variant variant) {
		variantDao.deleteVariant(variant);
	}

}
