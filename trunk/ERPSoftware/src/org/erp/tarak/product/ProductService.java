package org.erp.tarak.product;

import java.util.List;


public interface ProductService {
	
	public void addProduct(Product product);

	public List<Product> listProducts();
	
	public Product getProduct(long productId);
	
	public void deleteProduct(Product product);

	public List<Product> listProductsbyCategory(long category);

	public Product getLastProduct();

	public Product getLastProductByCategory(long categoryId);

	public Product getProductByName(String productName);

	public List<Product> listProductsbyCategory();
}
