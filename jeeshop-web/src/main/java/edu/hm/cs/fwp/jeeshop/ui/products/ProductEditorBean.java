/*
 * ProductEditorBean.java
 * jeeshop-web
 */
package edu.hm.cs.fwp.jeeshop.ui.products;

import java.io.Serializable;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import edu.hm.cs.fwp.jeeshop.business.products.boundary.ProductManagerBean;
import edu.hm.cs.fwp.jeeshop.business.products.entity.Product;

/**
 * TODO: dokumentieren !!!
 * 
 * @author theism
 *
 */
@Named
@ViewScoped
public class ProductEditorBean implements Serializable {

	@Inject
	private ProductManagerBean boundary;

	private long productId;

	private Product product;

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public long getProductId() {
		return productId;
	}

	/**
	 * Wird immer vor der Anzeige des Views aufgerufen, alle View-Parameter sind
	 * initialisiert worden.
	 */
	public void onPreRenderView() {
		if (this.product == null) {
			if (this.productId != 0L) {
				// Produkt anzeigen
				this.product = this.boundary.retrieveProductById(this.productId);
			} else {
				// Produkt anlegen
				this.product = new Product();
			}
		}
	}

	public Product getProduct() {
		return product;
	}

	public String addProduct() {
		this.boundary.addProduct(this.product);
		return "viewProduct?faces-redirect=true&productId=" + this.product.getId();
	}
	
	public String closeProduct() {
		this.product = null;
		return "/home/home?faces-redirect=true";
	}
}
