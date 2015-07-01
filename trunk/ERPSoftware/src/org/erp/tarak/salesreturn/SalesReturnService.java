package org.erp.tarak.salesreturn;

import java.util.List;


public interface SalesReturnService {

	public void addSalesReturn(SalesReturn salesReturn);

	public List<SalesReturn> listSalesReturns(String finYear);

	public SalesReturn getSalesReturn(long salesReturnId,String finYear);

	public void deleteSalesReturn(SalesReturn salesReturn);

	public  List<SalesReturn> listSalesReturnsByCustomer(long customerId,String finYear);
}
