package org.erp.tarak.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service("productService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addProduct(Product product) {
		productDao.addProduct(product);
	}
	
	public List<Product> listProducts() {
		return productDao.listProducts();
	}

	public Product getProduct(long productId) {
		return productDao.getProduct(productId);
	}
	
	public void deleteProduct(Product product) {
		productDao.deleteProduct(product);
	}

	@Override
	public List<Product> listProductsbyCategory(long category) {
		return productDao.listProductsbyCategory(category);
	}

	@Override
	public Product getLastProduct() {
		return productDao.getLastProduct();
	}

	@Override
	public Product getLastProductByCategory(long categoryId) {
		return productDao.getLastProductByCategory(categoryId);
	}

	@Override
	public Product getProductByName(String productName) {
		return productDao.getProductByName(productName);
	}

	@Override
	public List<Product> listProductsbyCategory() {
		return productDao.listProductsbyCategory();
	}

}
