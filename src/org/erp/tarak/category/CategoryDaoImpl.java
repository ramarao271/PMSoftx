package org.erp.tarak.category;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("categoryDao")
public class CategoryDaoImpl implements CategoryDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void addCategory(Category category) {
		if (category.getCategoryId() > 0) {
			sessionFactory.getCurrentSession().update(category);
		} else {
			sessionFactory.getCurrentSession().save(category);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Category> listCategories() {
		return (List<Category>) sessionFactory.getCurrentSession()
				.createCriteria(Category.class).list();
	}

	public Category getCategory(long categoryId) {
		return (Category) sessionFactory.getCurrentSession().get(
				Category.class, categoryId);
	}

	public void deleteCategory(Category category) {
		sessionFactory
				.getCurrentSession()
				.createQuery(
						"DELETE FROM Category WHERE categoryId = "
								+ category.getCategoryId()).executeUpdate();
	}

	@Override
	public Category getLastCategory() {
		String hql = "from Category where categoryId=(SELECT MAX(categoryId) from Category)";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List results = query.list();
		if (results.size() > 0) {
			return (Category) results.get(0);
		}
		return null;
	}

	@Override
	public Category getCategoryByName(String categoryName) {
		String hql = "from Category where categoryName='"+categoryName+"')";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List results = query.list();
		if (results.size() > 0) {
			return (Category) results.get(0);
		}
		return null;
		
	}

}
