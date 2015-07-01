package org.erp.tarak.rawMaterial;

import java.util.List;


public interface RawMaterialService {
	
	public void addRawMaterial(RawMaterial rawMaterial);

	public List<RawMaterial> listRawMaterials();
	
	public RawMaterial getRawMaterial(long rawMaterialId);
	
	public void deleteRawMaterial(RawMaterial rawMaterial);

	public List<RawMaterial> listRawMaterialsbyCategory(long category);

	public RawMaterial getLastRawMaterial();

	public RawMaterial getLastRawMaterialByCategory(long categoryId);

	public RawMaterial getRawMaterialByName(String rawMaterialName);
}
