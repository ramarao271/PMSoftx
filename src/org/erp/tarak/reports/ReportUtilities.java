package org.erp.tarak.reports;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.erp.tarak.product.ProductReport;

public class ReportUtilities {
	public static List<ProductReport> populateProductSaleEntries(List<Object[]> obj, String type)
	{
		List<ProductReport> prList=new LinkedList<ProductReport>();
		if("Sales".equals(type))
		{
			for(Object[] x: obj)
			{
				ProductReport pr=new ProductReport();
				pr.setProductName((String)x[0]);
				pr.setCategoryName((String)x[1]);
				pr.setQuantity((double)x[2]);
				pr.setRate((double)x[3]);
				if(x[4] instanceof Date)
				{
					pr.setDate((Date)x[4]);
				}
				else if(x[4] instanceof Integer)
				{
					pr.setMonth((int)x[4]);
				}
				prList.add(pr);
			}
		}
		else if("Profit".equals(type))
		{
			for(Object[] x: obj)
			{
				//.addScalar("categoryName").addScalar("quantity").addScalar("totalPrice").addScalar("cost").addScalar("Date");
				ProductReport pr=new ProductReport();
				pr.setCategoryName((String)x[0]);
				pr.setQuantity((double)x[1]);
				pr.setTotalCost((double)x[2]);
				pr.setRate((double)x[3]);
				pr.setDate((Date)x[4]);
			}
		}
		return prList;	
	}
}
