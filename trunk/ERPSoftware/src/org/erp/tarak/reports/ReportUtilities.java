package org.erp.tarak.reports;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.erp.tarak.library.ERPConstants;
import org.erp.tarak.product.ProductReport;

public class ReportUtilities {
	public static List<ProductReport> populateProductSaleEntries(List<Object[]> obj, String reportType, int timeType,String groupBy)
	{
		List<ProductReport> prList=new LinkedList<ProductReport>();
		if("Sales".equals(reportType))
		{
			for(Object[] x: obj)
			{
				ProductReport pr=new ProductReport();
				pr.setProductName((String)x[0]);
				if(ERPConstants.CATEGORY.equals(groupBy))
				{
					pr.setCategoryName((String)x[1]);
				}
				else
				{
					pr.setVariantName((String)x[1]);
					pr.setVariantType((String)x[5]);
				}
				pr.setQuantity((double)x[2]);
				pr.setRate((double)x[3]);
				if(x[4] instanceof Date)
				{
					pr.setDate((Date)x[4]);
				}
				else if(x[4] instanceof String)
				{
					pr.setMonth((String)x[4]);
				}
				else if(x[4] instanceof Integer)
				{
					pr.setYear((int)x[4]);
				}
				prList.add(pr);
			}
		}
		else if("Profit".equals(reportType))
		{
			for(Object[] x: obj)
			{
				ProductReport pr=new ProductReport();
				pr.setProductName((String)x[0]);
				if(ERPConstants.CATEGORY.equals(groupBy))
				{
					pr.setCategoryName((String)x[1]);
				}
				else
				{
					pr.setVariantName((String)x[1]);
					pr.setVariantType((String)x[6]);
				}
				
				pr.setQuantity((double)x[2]);
				pr.setTotalCost((double)x[3]);
				pr.setRate((double)x[4]);
				if(x[5] instanceof Date)
				{
					pr.setDate((Date)x[5]);
				}
				else if(x[5] instanceof String)
				{
					pr.setMonth((String)x[5]);
				}
				else if(x[5] instanceof Integer)
				{
					pr.setYear((int)x[5]);
				}
				prList.add(pr);
			}
		}
		List<ProductReport> prList1=new ArrayList<ProductReport>();
		Date d=null;
		String month=null;
		int year=0;
		for(ProductReport pp: prList)
		{
			if(timeType==Calendar.DATE)
			{
				if(d==null || d.compareTo(pp.getDate())!=0)
				{
					ProductReport pr=new ProductReport();
					pr.setProductName(pp.getDate()+"");
					prList1.add(pr);
					d=pp.getDate();
				}
				prList1.add(pp);
			}
			else if(timeType==Calendar.MONTH)
			{
				if(month==null || !pp.getMonth().equals(month))
				{
					ProductReport pr=new ProductReport();
					pr.setProductName(pp.getMonth()+"");
					prList1.add(pr);
					month=pp.getMonth();
				}
				prList1.add(pp);
			}
			else if(timeType==Calendar.YEAR)
			{
				if(year==0 || !(pp.getYear()==year))
				{
					ProductReport pr=new ProductReport();
					pr.setProductName(pp.getYear()+"");
					prList1.add(pr);
					year=pp.getYear();
				}
				prList1.add(pp);
			}
		}

		return prList1;	
	}

	public static int getDurationType(String durationType) {
		int type=0;
		if("Daily".equals(durationType))
		{
			type=Calendar.DATE;
		}
		else if("Monthly".equals(durationType))
		{
			type=Calendar.MONTH;
		}
		else if("Yearly".equals(durationType))
		{
			type=Calendar.YEAR;
		}
		return type;
	}
}
