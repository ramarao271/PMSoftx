package org.erp.tarak.productionorder;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service("productionOrderItemService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ProductionOrderItemServiceImpl implements ProductionOrderItemService {

	@Autowired
	private ProductionOrderItemDao productionOrderItemDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addProductionOrderItem(ProductionOrderItem productionOrderItem) {
		productionOrderItemDao.addProductionOrderItem(productionOrderItem);
	}
	
	public List<ProductionOrderItem> listProductionOrderItems() {
		return productionOrderItemDao.listProductionOrderItems();
	}

	public ProductionOrderItem getProductionOrderItem(long productionOrderItemId) {
		return productionOrderItemDao.getProductionOrderItem(productionOrderItemId);
	}
	
	public void deleteProductionOrderItem(ProductionOrderItem productionOrderItem) {
		productionOrderItemDao.deleteProductionOrderItem(productionOrderItem);
	}

	@Override
	public void deleteProductionOrderItems(List<ProductionOrderItem> pois) {
		// TODO Auto-generated method stub
		productionOrderItemDao.deleteProductionOrderItems(pois);
	}

}
