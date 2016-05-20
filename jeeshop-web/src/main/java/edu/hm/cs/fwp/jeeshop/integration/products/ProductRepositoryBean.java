/*
 * ProductRepositoryBean.java
 * jeeshop-web
 */
package edu.hm.cs.fwp.jeeshop.integration.products;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import edu.hm.cs.fwp.jeeshop.business.products.entity.Product;

/**
 * Repository für {@link Product}s.
 * @author theism
 *
 */
@Stateless
public class ProductRepositoryBean {

	@PersistenceContext
	private EntityManager entityManager;
	
 	public void add(Product newProduct) {
 		this.entityManager.persist(newProduct);
 	}
	
 	public Product get(long productId) {
 		return this.entityManager.find(Product.class, productId);
 	}
	
 	public void set(Product existingProduct) {
 		this.entityManager.merge(existingProduct);
 	}
 	
 	public void remove(Product existingProduct) {
 		this.entityManager.remove(existingProduct);
 	}
 	
 	public List<Product> getAllProducts() {
 		TypedQuery<Product> query = this.entityManager.createQuery("select p from Product p", Product.class);
 		return query.getResultList();
 	}
 	
 	public List<Product> getAllProducts(int startPosition, int maxResult) {
 		TypedQuery<Product> query = this.entityManager.createNamedQuery(Product.QUERY_ALL, Product.class);
 		query.setFirstResult(startPosition);
 		query.setMaxResults(maxResult);
 		return query.getResultList();
 	}
}
