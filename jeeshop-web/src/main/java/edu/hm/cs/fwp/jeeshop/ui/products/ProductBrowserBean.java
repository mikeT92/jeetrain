/*
 * ProductBrowserBean.java
 * jeeshop-web
 */
package edu.hm.cs.fwp.jeeshop.ui.products;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.print.attribute.standard.Severity;

import edu.hm.cs.fwp.jeeshop.business.products.boundary.ProductFinderBean;
import edu.hm.cs.fwp.jeeshop.business.products.entity.Product;

/**
 * {@code Controller} für den View {@code browseProducts}.
 * 
 * @author theism
 *
 */
@Named("productBrowser")
@ViewScoped
public class ProductBrowserBean implements Serializable {

	private static final int MAX_PAGE_SIZE = 20;
	private List<Product> products;

	@Inject
	private ProductFinderBean finder;

	public void onPreRenderView() {
		if (this.products == null) {
			try {
				this.products = this.finder.findProducts(0, MAX_PAGE_SIZE);
			} catch (Exception ex) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Laden fehlgeschlagen",
						ex.getMessage());
				FacesContext.getCurrentInstance().addMessage(null, message);
			}
		}
		if (this.products == null) {
			this.products = Collections.emptyList();
		}
	}

	/**
	 * Liste der verfügbaren Produkte.
	 */
	public List<Product> getProducts() {
		return products;
	}
}
