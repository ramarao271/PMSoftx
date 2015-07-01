package org.erp.tarak.productionorder;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service("productionOrderService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ProductionOrderServiceImpl implements ProductionOrderService {

	@Autowired
	private ProductionOrderDao productionOrderDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addProductionOrder(ProductionOrder productionOrder) {
		productionOrderDao.addProductionOrder(productionOrder);
	}
	
	public List<ProductionOrder> listProductionOrders() {
		return productionOrderDao.listProductionOrders();
	}

	public ProductionOrder getProductionOrder(long productionOrderId) {
		return productionOrderDao.getProductionOrder(productionOrderId);
	}
	
	public void deleteProductionOrder(ProductionOrder productionOrder) {
		productionOrderDao.deleteProductionOrder(productionOrder);
	}

	@Override
	public List<ProductionOrder> listPendingProductionOrders() {
		return productionOrderDao.listPendingProductionOrders();
		}

	@Override
	public List<ProductionOrder> listProcessedProductionOrders() {
		return productionOrderDao.listProcessedProductionOrders();
	}

}
