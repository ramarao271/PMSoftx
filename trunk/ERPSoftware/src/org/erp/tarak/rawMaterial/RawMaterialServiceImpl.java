package org.erp.tarak.rawMaterial;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service("rawMaterialService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class RawMaterialServiceImpl implements RawMaterialService {

	@Autowired
	private RawMaterialDao rawMaterialDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addRawMaterial(RawMaterial rawMaterial) {
		rawMaterialDao.addRawMaterial(rawMaterial);
	}
	
	public List<RawMaterial> listRawMaterials() {
		return rawMaterialDao.listRawMaterials();
	}

	public RawMaterial getRawMaterial(long rawMaterialId) {
		return rawMaterialDao.getRawMaterial(rawMaterialId);
	}
	
	public void deleteRawMaterial(RawMaterial rawMaterial) {
		rawMaterialDao.deleteRawMaterial(rawMaterial);
	}

	@Override
	public List<RawMaterial> listRawMaterialsbyCategory(long category) {
		return rawMaterialDao.listRawMaterialsbyCategory(category);
	}

	@Override
	public RawMaterial getLastRawMaterial() {
		return rawMaterialDao.getLastRawMaterial();
	}

	@Override
	public RawMaterial getLastRawMaterialByCategory(long categoryId) {
		return rawMaterialDao.getLastRawMaterialByCategory(categoryId);
	}

	@Override
	public RawMaterial getRawMaterialByName(String rawMaterialName) {
		return rawMaterialDao.getRawMaterialByName(rawMaterialName);
	}

}
