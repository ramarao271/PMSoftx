package org.erp.tarak.salesorder;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("salesOrderItemDao")
public class SalesOrderItemDaoImpl implements SalesOrderItemDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void addSalesOrderItem(SalesOrderItem salesOrderItem) {
		if (salesOrderItem.getSalesOrderId() > 0) {
			sessionFactory.getCurrentSession().update(salesOrderItem);
		} else {
			sessionFactory.getCurrentSession().save(salesOrderItem);
		}

	}

	@SuppressWarnings("unchecked")
	public List<SalesOrderItem> listSalesOrderItems() {
		return (List<SalesOrderItem>) sessionFactory.getCurrentSession()
				.createCriteria(SalesOrderItem.class).list();
	}

	public SalesOrderItem getSalesOrderItem(long empid) {
		return (SalesOrderItem) sessionFactory.getCurrentSession().get(SalesOrderItem.class,
				empid);
	}

	public void deleteSalesOrderItem(SalesOrderItem salesOrderItem) {
		sessionFactory
				.getCurrentSession()
				.createQuery(
						"DELETE FROM SalesOrderItem WHERE salesOrderId = "
								+ salesOrderItem.getSalesOrderId()).executeUpdate();
	}

	@Override
	public void deleteSalesOrderItems(List<SalesOrderItem> pois) {
		for(SalesOrderItem poi:pois)
		{
			deleteSalesOrderItem(poi);
		}
		
	}

	@Override
	public List<Object[]> listLostSalesReportByCategoryWise(String finYear,
			int type, String fromDate,String toDate) {
		Query query=null;
		if(type==Calendar.DATE)
		{
			String hql="select p.product_Name as productName, c.category_name as categoryName,sum(si.quantity) as quantity,sum(si.totalCost) as Amount,s.so_date as Date from salesOrderitem si,Product p,salesOrder s,category c where s.processed=0 and p.product_id=si.product_Id and financial_year='"+finYear+"' and si.salesOrderId=s.salesOrderId and c.categoryid=p.category_Id and s.so_date between '"+fromDate+"' and '"+toDate+"' group by s.so_date,p.category_id order by s.so_date;";
			query = sessionFactory.getCurrentSession().createSQLQuery(hql).addScalar("productName").addScalar("categoryName").addScalar("quantity").addScalar("Amount").addScalar("Date");
		}
		else if(type==Calendar.MONTH)
		{
			String hql="select p.product_Name as productName, c.category_name as categoryName,sum(si.quantity) as quantity,sum(si.totalCost) as Amount,monthname(s.so_date) as Date from salesOrderitem si,Product p,salesOrder s,category c where s.processed=0 and p.product_id=si.product_Id and financial_year='"+finYear+"' and si.salesOrderId=s.salesOrderId and c.categoryid=p.category_Id and s.so_date between '"+fromDate+"' and '"+toDate+"' group by monthname(s.so_date),p.category_id,p.product_id order by monthname(s.so_date),p.category_id;";
			query = sessionFactory.getCurrentSession().createSQLQuery(hql).addScalar("productName").addScalar("categoryName").addScalar("quantity").addScalar("Amount").addScalar("Date");
		}
		else if(type==Calendar.YEAR)
		{
			String hql="select p.product_Name as productName, c.category_name as categoryName,sum(si.quantity) as quantity,sum(si.totalCost) as Amount,year(s.so_date) as Date from salesOrderitem si,Product p,salesOrder s,category c where s.processed=0 and p.product_id=si.product_Id and si.salesOrderId=s.salesOrderId and c.categoryid=p.category_Id and s.so_date between '"+fromDate+"' and '"+toDate+"' group by year(s.so_date),p.category_id,p.product_id order by year(s.so_date),p.category_id;";
			query = sessionFactory.getCurrentSession().createSQLQuery(hql).addScalar("productName").addScalar("categoryName").addScalar("quantity").addScalar("Amount").addScalar("Date");
		}
		List results = query.list();
		return results;	
	}

	@Override
	public List<Object[]> listLostSalesReportByCategoryWise(String finYear,
			int type, String date) {
		Query query=null;
		if(type==Calendar.DATE)
		{
			String hql="select p.product_Name as productName, c.category_name as categoryName,sum(si.quantity) as quantity,sum(si.totalCost) as Amount,s.so_date as Date from salesOrderItem si,Product p,salesOrder s,category c where s.processed=0 and p.product_id=si.product_Id and financial_year='"+finYear+"' and si.salesOrderId=s.salesOrderId and c.categoryid=p.category_Id and s.so_date='"+date+"' group by s.so_date,p.category_id,p.product_Id order by s.so_date;";
			query = sessionFactory.getCurrentSession().createSQLQuery(hql).addScalar("productName").addScalar("categoryName").addScalar("quantity").addScalar("Amount").addScalar("Date");
		}	
		List results = query.list();
		return results;

	}
	@Override
	public List<Object[]> listLostSalesReportByVariantWise(String finYear,
			int type, String fromDate,String toDate) {
		Query query=null;
		if(type==Calendar.DATE)
		{
			String hql="select p.product_Name as productName, v.variantname as variantName,sum(si.quantity) as quantity,sum(si.totalCost) as Amount,s.so_date as Date,v.variantType as variantType from salesOrderitem si,Product p,salesOrder s,variant v where s.processed=0 and financial_year='"+finYear+"' and p.product_id=si.product_Id and si.salesOrderId=s.salesOrderId and v.variantId=si.variantId and s.so_date between '"+fromDate+"' and '"+toDate+"' group by s.so_date,v.variantType,p.product_id order by s.so_date,v.variantType;";
			query = sessionFactory.getCurrentSession().createSQLQuery(hql).addScalar("productName").addScalar("variantName").addScalar("quantity").addScalar("Amount").addScalar("Date").addScalar("variantType");
		}
		else if(type==Calendar.MONTH)
		{
			String hql="select p.product_Name as productName, v.variantname as variantName,sum(si.quantity) as quantity,sum(si.totalCost) as Amount,monthname(s.so_date) as	Date,v.variantType as variantType from salesOrderitem si,Product p,salesOrder s,variant v where s.processed=0 and financial_year='"+finYear+"' and p.product_id=si.product_Id and si.salesOrderId=s.salesOrderId and v.variantId=si.variantId and s.so_date between '"+fromDate+"' and '"+toDate+"' group by monthname(s.so_date),v.variantType,p.product_id order by monthname(s.so_date),v.variantType;";
			query = sessionFactory.getCurrentSession().createSQLQuery(hql).addScalar("productName").addScalar("variantName").addScalar("quantity").addScalar("Amount").addScalar("Date").addScalar("variantType");
		}
		else if(type==Calendar.YEAR)
		{
			String hql="select p.product_Name as productName, v.variantname as variantName,sum(si.quantity) as quantity,sum(si.totalCost) as Amount,year(s.so_date) as	Date,v.variantType as variantType from salesOrderitem si,Product p,salesOrder s,variant v where s.processed=0 and p.product_id=si.product_Id and si.salesOrderId=s.salesOrderId and v.variantId=si.variantId and s.so_date between '"+fromDate+"' and '"+toDate+"' group by year(s.so_date),v.variantType,p.product_id order by year(s.so_date),v.variantType;";
			query = sessionFactory.getCurrentSession().createSQLQuery(hql).addScalar("productName").addScalar("variantName").addScalar("quantity").addScalar("Amount").addScalar("Date").addScalar("variantType");
		}
		List results = query.list();
		return results;	
	}

	@Override
	public List<Object[]> listLostSalesReportByVariantWise(String finYear,
			int type, String date) {
		Query query=null;
		if(type==Calendar.DATE)
		{
			String hql="select p.product_Name as productName, v.variantname as variantName,sum(si.quantity) as quantity,sum(si.totalCost) as Amount,s.so_date as Date,v.variantType as variantType from salesOrderitem si,Product p,salesOrder s,variant v where s.processed=0 and financial_year='"+finYear+"' and p.product_id=si.product_Id and si.salesOrderId=s.salesOrderId and v.variantId=si.variantId and s.so_date='"+date+"' group by s.so_date,v.variantType,p.product_id order by s.so_date,v.variantType;";
			query = sessionFactory.getCurrentSession().createSQLQuery(hql).addScalar("productName").addScalar("variantName").addScalar("quantity").addScalar("Amount").addScalar("Date").addScalar("variantType");
		}	
		List results = query.list();
		return results;

	}
	

}
