package org.erp.tarak.purchasereturn;

import java.util.Date;
import java.util.List;


public interface PurchaseReturnService {

	public void addPurchaseReturn(PurchaseReturn purchaseReturn);

	public List<PurchaseReturn> listPurchaseReturns(String finYear);

	public PurchaseReturn getPurchaseReturn(long purchaseReturnId,String finYear);

	public void deletePurchaseReturn(PurchaseReturn purchaseReturn);

	public  List<PurchaseReturn> listPurchaseReturnsBySupplier(long supplierId,String finYear);

	public List<PurchaseReturn> listPurchaseReturns(Date balanceSheetDate);
}
