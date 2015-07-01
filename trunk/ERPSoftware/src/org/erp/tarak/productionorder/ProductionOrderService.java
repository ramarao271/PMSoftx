package org.erp.tarak.productionorder;

import java.util.List;


public interface ProductionOrderService {

	public void addProductionOrder(ProductionOrder productionOrder);

	public List<ProductionOrder> listProductionOrders();

	public ProductionOrder getProductionOrder(long productionOrderId);

	public void deleteProductionOrder(ProductionOrder productionOrder);

	public List<ProductionOrder> listPendingProductionOrders();

	public List<ProductionOrder> listProcessedProductionOrders();
}
