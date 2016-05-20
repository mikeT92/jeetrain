/*
 * ProductManagerBean.java
 * jeeshop-web
 */
package edu.hm.cs.fwp.jeeshop.business.products.boundary;

import javax.ejb.Stateless;
import javax.inject.Inject;

import edu.hm.cs.fwp.jeeshop.business.products.entity.Product;
import edu.hm.cs.fwp.jeeshop.integration.GenericRepositoryBean;
import edu.hm.cs.fwp.jeeshop.integration.products.ProductRepositoryBean;

/**
 * TODO: dokumentieren !!!
 * 
 * @author theism
 *
 */
@Stateless
public class ProductManagerBean {

	@Inject
	private GenericRepositoryBean repository;

	public void addProduct(Product newProduct) {
		this.repository.addEntity(newProduct);
	}

	public Product retrieveProductById(long productId) {
		return this.repository.getEntityById(Product.class, productId);
	}

	public void removeProduct(long productId) {
		this.repository.removeEntityById(Product.class, productId);
	}
}
