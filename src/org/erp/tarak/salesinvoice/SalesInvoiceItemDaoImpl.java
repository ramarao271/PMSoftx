package org.erp.tarak.salesinvoice;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

import org.erp.tarak.category.CategoryReport;
import org.erp.tarak.customer.Customer;
import org.erp.tarak.product.Product;
import org.erp.tarak.variant.VariantReport;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("salesInvoiceItemDao")
public class SalesInvoiceItemDaoImpl implements SalesInvoiceItemDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void addSalesInvoiceItem(SalesInvoiceItem salesInvoiceItem) {
		if (salesInvoiceItem.getSalesInvoiceId() > 0) {
			sessionFactory.getCurrentSession().update(salesInvoiceItem);
		} else {
			sessionFactory.getCurrentSession().save(salesInvoiceItem);
		}

	}

	@SuppressWarnings("unchecked")
	public List<SalesInvoiceItem> listSalesInvoiceItems(String finYear) {
		return (List<SalesInvoiceItem>) sessionFactory.getCurrentSession()
				.createCriteria(SalesInvoiceItem.class)
				.add(Restrictions.eq("finYear", finYear)).list();
	}

	public SalesInvoiceItem getSalesInvoiceItem(long empid, String finYear) {
		return (SalesInvoiceItem) sessionFactory.getCurrentSession()
				.createCriteria(SalesInvoiceItem.class)
				.add(Restrictions.eq("finYear", finYear))
				.add(Restrictions.eq("salesInvoiceId", empid)).list().get(0);
	}

	public void deleteSalesInvoiceItem(SalesInvoiceItem salesInvoiceItem) {
		sessionFactory
				.getCurrentSession()
				.createQuery(
						"DELETE FROM SalesInvoiceItem WHERE salesInvoiceId = "
								+ salesInvoiceItem.getSalesInvoiceId())
				.executeUpdate();
	}

	@Override
	public void deleteSalesInvoiceItems(List<SalesInvoiceItem> pois) {
		for (SalesInvoiceItem poi : pois) {
			deleteSalesInvoiceItem(poi);
		}

	}

	@Override
	public List<CategoryReport> getSalesReportByCategory(String finYear) {
		String hql = "select c.categoryId as categoryId,c.category_Name as category,sum(s.quantity) as quantity,sum(s.totalCost) as amount,c.Category_Code as categoryCode from salesInvoiceItem s,product p,category c where s.Financial_Year='"
				+ finYear
				+ "' and p.product_Id=s.product_Id and c.categoryId=p.category_id group by c.categoryid;";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(hql);
		List results = query.list();
		List<CategoryReport> cats = new LinkedList<CategoryReport>();
		List<Object[]> objects = (List<Object[]>) results;
		for (Object[] x : objects) {
			CategoryReport xx = new CategoryReport();
			xx.setCategoryId(((BigInteger) x[0]).longValue());
			xx.setCategory((String) x[1]);
			xx.setQuantity((double) x[2]);
			xx.setAmount((double) x[3]);
			xx.setCategoryCode((String) x[4]);
			cats.add(xx);
		}
		return cats;
	}

	@Override
	public List<Object[]> listSalesInvoiceItemsByCategory(long id,
			String finYear) {
		String hql = "select {p.*},sum(s.quantity) as quantity ,sum(s.totalCost) as cost from salesInvoiceItem s,product p,category c where s.Financial_Year='"
				+ finYear
				+ "' and p.product_Id=s.product_Id and c.categoryId=p.category_id and c.categoryId="
				+ id + " group by s.product_Id;";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(hql)
				.addEntity("p", Product.class).addScalar("quantity")
				.addScalar("cost");
		List results = query.list();
		return results;
	}

	@Override
	public List<Object[]> listFrequesntlyProductsByCustomer(long customerId,
			String finYear) {
		String hql = "select {c.*},{p.*},count(s.*) as count from salesInvoiceItem s,product p,Customer c where s.Financial_Year='"
				+ finYear
				+ "' and s.customer_id=c.customerId group by s.product_id order by s.customer_Id;";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(hql)
				.addEntity("c", Customer.class).addEntity("p", Product.class)
				.addScalar("count");
		List results = query.list();
		return results;
	}

	@Override
	public List<VariantReport> listFrequesntlyProductsByVariant(String finYear) {
		String hql = "select v.variantType,s.variantId,sum(s.quantity),sum(s.totalcost) from salesinvoiceitem s,variant v where s.variantId=v.variantId and s.Financial_Year='"
				+ finYear + "' group by v.variantType;";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(hql);
		List results = query.list();
		List<VariantReport> cats = new LinkedList<VariantReport>();
		List<Object[]> objects = (List<Object[]>) results;
		for (Object[] x : objects) {
			VariantReport xx = new VariantReport();
			xx.setVariantId(((BigInteger) x[1]).longValue());
			xx.setVariantType((String) x[0]);
			xx.setQuantity((double) x[2]);
			xx.setAmount((double) x[3]);
			xx.setVariantCode((String) x[4]);
			cats.add(xx);
		}
		return cats;
	}

	@Override
	public List<VariantReport> getSalesReportByVariant(String finYear) {
		String hql = "select v.variantType,sum(s.quantity),sum(s.totalcost) from salesinvoiceitem s,variant v where s.variantId=v.variantId and s.Financial_Year='"
				+ finYear + "' group by v.variantType;";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(hql);
		List results = query.list();
		List<VariantReport> cats = new LinkedList<VariantReport>();
		List<Object[]> objects = (List<Object[]>) results;
		for (Object[] x : objects) {
			VariantReport xx = new VariantReport();
			xx.setVariantType((String) x[0]);
			xx.setQuantity((double) x[1]);
			xx.setAmount((double) x[2]);
			cats.add(xx);
		}
		return cats;
	}
}
