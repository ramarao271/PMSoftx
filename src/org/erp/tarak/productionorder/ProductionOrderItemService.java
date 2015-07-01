package org.erp.tarak.productionorder;

import java.util.List;


public interface ProductionOrderItemService {
	public void addProductionOrderItem(ProductionOrderItem productionOrderItem);

	public List<ProductionOrderItem> listProductionOrderItems();

	public ProductionOrderItem getProductionOrderItem(long productionOrderItemId);

	public void deleteProductionOrderItem(ProductionOrderItem productionOrderItem);

	public void deleteProductionOrderItems(List<ProductionOrderItem> pois);

}
