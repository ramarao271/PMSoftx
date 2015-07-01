package org.erp.tarak.purchasereturn;

import java.util.List;


public interface PurchaseReturnDao {
	
	public void addPurchaseReturn(PurchaseReturn purchaseReturn);

	public List<PurchaseReturn> listPurchaseReturns(String finYear);
	
	public PurchaseReturn getPurchaseReturn(long purchaseReturnId,String finYear);
	
	public void deletePurchaseReturn(PurchaseReturn purchaseReturn);

	public List<PurchaseReturn> listPurchaseReturnsBySupplier(long supplierId,String finYear);
}
