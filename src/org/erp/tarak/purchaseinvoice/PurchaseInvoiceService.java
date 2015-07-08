package org.erp.tarak.purchaseinvoice;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;


public interface PurchaseInvoiceService {

	public void addPurchaseInvoice(PurchaseInvoice purchaseInvoice);

	public List<PurchaseInvoice> listPurchaseInvoices(String finYear);

	public PurchaseInvoice getPurchaseInvoice(long purchaseInvoiceId,String finYear);

	public void deletePurchaseInvoice(PurchaseInvoice purchaseInvoice);

	public  List<PurchaseInvoice> listPurchaseInvoicesBySupplier(long supplierId,String finYear);

	public void updatePurchaseInvoiceBalance(PurchaseInvoice purchaseInvoice);

	public List<PurchaseInvoice> listPendingPurchaseInvoices(String finYear);

	public List<PurchaseInvoice> listPurchaseInvoicesBySupplier(String finYear);
	
	public List<Long> listBilledSuppliers(String finYear);

	public List<Object[]> listPendingPurchaseInvoicesBySupplier(String finYear);

	public List<PurchaseInvoice> listPurchaseInvoicesByDate(Date balanceSheetDate);
}
