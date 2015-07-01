package org.erp.tarak.rawMaterial;

import java.util.List;

import org.erp.tarak.category.Category;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Repository("rawMaterialDao")
public class RawMaterialDaoImpl implements RawMaterialDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	public void addRawMaterial(RawMaterial rawMaterial) {
		sessionFactory.getCurrentSession().saveOrUpdate(rawMaterial);
	}

	@SuppressWarnings("unchecked")
	public List<RawMaterial> listRawMaterials() {
		return (List<RawMaterial>) sessionFactory.getCurrentSession().createCriteria(RawMaterial.class).list();
	}

	public RawMaterial getRawMaterial(long rawMaterialId) {
		return (RawMaterial) sessionFactory.getCurrentSession().get(RawMaterial.class, rawMaterialId);
	}

	public void deleteRawMaterial(RawMaterial rawMaterial) {
		sessionFactory.getCurrentSession().createQuery("DELETE FROM RawMaterial WHERE RawMaterial_Id = "+rawMaterial.getRawMaterialId()).executeUpdate();
	}

	@Override
	public List<RawMaterial> listRawMaterialsbyCategory(long category) {
		Category cat=new Category();
		cat.setCategoryId(category);
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				RawMaterial.class);
		crit.add(Restrictions.eq("category", cat));
		List results = crit.list();
		return results;
	}

	@Override
	public RawMaterial getLastRawMaterial() {
		String hql = "from RawMaterial where rawMaterialId=(SELECT MAX(rawMaterialId) from RawMaterial)";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List results = query.list();
		if (results.size() > 0) {
			return (RawMaterial) results.get(0);
		}
		return null;
	}

	@Override
	public RawMaterial getLastRawMaterialByCategory(long categoryId) {
		String hql = "from RawMaterial where rawMaterialId=(SELECT MAX(rawMaterialId) from RawMaterial where Category_Id="+categoryId+" )";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List results = query.list();
		if (results.size() > 0) {
			return (RawMaterial) results.get(0);
		}
		return null;
	}

	@Override
	public RawMaterial getRawMaterialByName(String rawMaterialName) {
		String hql = "from RawMaterial where rawMaterialName='"+rawMaterialName+"'";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List results = query.list();
		if (results.size() > 0) {
			return (RawMaterial) results.get(0);
		}
		return null;
	}
}
