package org.erp.tarak.purchaseinvoice;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

import org.erp.tarak.category.CategoryReport;
import org.erp.tarak.supplier.Supplier;
import org.erp.tarak.variant.VariantReport;
import org.erp.tarak.product.Product;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("purchaseInvoiceItemDao")
public class PurchaseInvoiceItemDaoImpl implements PurchaseInvoiceItemDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void addPurchaseInvoiceItem(PurchaseInvoiceItem purchaseInvoiceItem) {
		if (purchaseInvoiceItem.getPurchaseInvoiceId() > 0) {
			sessionFactory.getCurrentSession().update(purchaseInvoiceItem);
		} else {
			sessionFactory.getCurrentSession().save(purchaseInvoiceItem);
		}

	}

	@SuppressWarnings("unchecked")
	public List<PurchaseInvoiceItem> listPurchaseInvoiceItems(String finYear) {
		return (List<PurchaseInvoiceItem>) sessionFactory.getCurrentSession()
				.createCriteria(PurchaseInvoiceItem.class).add(Restrictions.eq("finYear",finYear)).list();
	}

	public PurchaseInvoiceItem getPurchaseInvoiceItem(long empid,String finYear) {
		return (PurchaseInvoiceItem) sessionFactory.getCurrentSession()
				.createCriteria(PurchaseInvoiceItem.class).add(Restrictions.eq("finYear",finYear)).add(Restrictions.eq("purchaseInvoiceId",empid)).list().get(0);
	}

	public void deletePurchaseInvoiceItem(PurchaseInvoiceItem purchaseInvoiceItem) {
		sessionFactory
				.getCurrentSession()
				.createQuery(
						"DELETE FROM PurchaseInvoiceItem WHERE purchaseInvoiceId = "
								+ purchaseInvoiceItem.getPurchaseInvoiceId()).executeUpdate();
	}

	@Override
	public void deletePurchaseInvoiceItems(List<PurchaseInvoiceItem> pois) {
		for(PurchaseInvoiceItem poi:pois)
		{
			deletePurchaseInvoiceItem(poi);
		}
		
	}

	@Override
	public List<CategoryReport> getPurchaseReportByCategory(String finYear) {
		String hql = "select c.categoryId as categoryId,c.category_Name as category,sum(s.quantity) as quantity,sum(s.totalCost) as amount,c.Category_Code as categoryCode from purchaseInvoiceItem s,product p,category c where s.Financial_Year='"+finYear+"' and p.product_Id=s.product_Id and c.categoryId=p.category_id group by c.categoryid;";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(hql);
		List results = query.list();
		List<CategoryReport> cats=new LinkedList<CategoryReport>();
			List<Object[]> objects	= (List<Object[]>)results;
			for(Object[] x: objects)
			{
				CategoryReport xx=new CategoryReport();
				xx.setCategoryId(((BigInteger) x[0]).longValue());
				xx.setCategory((String) x[1]);
				xx.setQuantity((double) x[2]);
				xx.setAmount((double) x[3]);
				xx.setCategoryCode((String)x[4]);
				cats.add(xx);
			}
		return cats;
	}

	@Override
	public List<Object[]> listPurchaseInvoiceItemsByCategory(long id,
			String finYear) {
		String hql = "select {p.*},sum(s.quantity) as quantity ,sum(s.totalCost) as cost from purchaseInvoiceItem s,product p,category c where s.Financial_Year='"+finYear+"' and p.product_Id=s.product_Id and c.categoryId=p.category_id and c.categoryId="+id+" group by s.product_Id;";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(hql).addEntity("p", Product.class).addScalar("quantity").addScalar("cost");
		List results = query.list();
		return results;
	}

	@Override
	public List<Object[]> listFrequesntlyProductsBySupplier(long supplierId,String finYear) {
		String hql = "select {c.*},{p.*},count(s.*) as count from purchaseInvoiceItem s,product p,Supplier c where s.Financial_Year='"+finYear+"' and s.supplier_id=c.supplierId group by s.product_id order by s.supplier_Id;";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(hql).addEntity("c",Supplier.class).addEntity("p", Product.class).addScalar("count");
		List results = query.list();
		return results;
	}

	@Override
	public List<VariantReport> getPurchaseReportByVariant(String finYear) {
		String hql = "select v.variantType,sum(s.quantity),sum(s.totalcost) from purchaseinvoiceitem s,variant v where s.variantId=v.variantId and s.Financial_Year='"
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
