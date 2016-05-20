/*
 * ProductFinderBean.java
 * jeeshop-web
 */
package edu.hm.cs.fwp.jeeshop.business.products.boundary;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import edu.hm.cs.fwp.jeeshop.business.products.entity.Product;
import edu.hm.cs.fwp.jeeshop.integration.GenericRepositoryBean;

/**
 * Einfacher Produkt Finder
 * 
 * @author theism
 *
 */
@Stateless
public class ProductFinderBean {

	@Inject
	GenericRepositoryBean repository;

	public List<Product> findProducts(int startPosition, int maxResult) {
		return this.repository.queryEntitiesWithNamedQuery(Product.class, Product.QUERY_ALL, null, startPosition,
				maxResult);
	}
}
