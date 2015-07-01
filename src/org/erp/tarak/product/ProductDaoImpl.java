package org.erp.tarak.product;

import java.util.List;

import org.erp.tarak.category.Category;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Repository("productDao")
public class ProductDaoImpl implements ProductDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	public void addProduct(Product product) {
		sessionFactory.getCurrentSession().saveOrUpdate(product);
	}

	@SuppressWarnings("unchecked")
	public List<Product> listProducts() {
		return (List<Product>) sessionFactory.getCurrentSession().createCriteria(Product.class).list();
	}

	public Product getProduct(long productId) {
		return (Product) sessionFactory.getCurrentSession().get(Product.class, productId);
	}

	public void deleteProduct(Product product) {
		sessionFactory.getCurrentSession().createQuery("DELETE FROM Product WHERE Product_Id = "+product.getProductId()).executeUpdate();
	}

	@Override
	public List<Product> listProductsbyCategory(long category) {
		Category cat=new Category();
		cat.setCategoryId(category);
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				Product.class);
		crit.add(Restrictions.eq("category", cat));
		List results = crit.list();
		return results;
	}

	@Override
	public Product getLastProduct() {
		String hql = "from Product where productId=(SELECT MAX(productId) from Product)";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List results = query.list();
		if (results.size() > 0) {
			return (Product) results.get(0);
		}
		return null;
	}

	@Override
	public Product getLastProductByCategory(long categoryId) {
		String hql = "from Product where productId=(SELECT MAX(productId) from Product where Category_Id="+categoryId+" )";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List results = query.list();
		if (results.size() > 0) {
			return (Product) results.get(0);
		}
		return null;
	}

	@Override
	public Product getProductByName(String productName) {
		String hql = "from Product where productName='"+productName+"'";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List results = query.list();
		if (results.size() > 0) {
			return (Product) results.get(0);
		}
		return null;
	}

	@Override
	public List<Product> listProductsbyCategory() {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				Product.class);
		crit.addOrder(Order.asc("category"));		
		List results = crit.list();
		return results;
	}
}
