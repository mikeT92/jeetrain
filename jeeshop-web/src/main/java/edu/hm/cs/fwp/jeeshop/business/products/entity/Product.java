/*
 * Product.java
 * jeeshop-web
 */
package edu.hm.cs.fwp.jeeshop.business.products.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

/**
 * Einfaches Produkt
 * 
 * @author theism
 *
 */
@Entity
@Table(name = "T_PRODUCT")
@NamedQueries({ @NamedQuery(name=Product.QUERY_ALL, query="select p from Product p") })
public class Product implements Serializable {

	public static final String QUERY_ALL = "edu.hm.cs.fwp.jeeshop.business.products.entity.Product.QUERY_ALL";
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Product.id.generator")
	@TableGenerator(name = "Product.id.generator", table = "T_SEQUENCE", pkColumnName = "SEQUENCE_NAME", pkColumnValue = "T_PRODUCT", valueColumnName = "NEXT_VAL", initialValue = 1000)
	@Column(name = "PRODUCT_ID")
	private long id;

	@NotNull
	@Size(min = 3, max = 255)
	@Column(name = "PRODUCT_NAME")
	private String name;

	@NotNull
	@Size(min = 3, max = 1500)
	@Column(name = "BESCHREIBUNG")
	private String beschreibung;

	@Column(name = "GROESSE")
	private String groesse;

	@NotNull
	@Min(0)
	@Column(name = "PREIS")
	private BigDecimal preis;

	@Column(name = "VERSION")
	@Version
	private int version;

	@Past
	@Column(name = "CREATION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;

	@Column(name = "LAST_MODIFICATION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModificationDate;

	/**
	 * Default-Konstruktor für JPA.
	 */
	public Product() {
		super();
	}

	public Product(long productId) {
		this.id = productId;
		this.creationDate = Calendar.getInstance().getTime();
		this.lastModificationDate = this.creationDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public String getGroesse() {
		return groesse;
	}

	public void setGroesse(String groesse) {
		this.groesse = groesse;
	}

	public BigDecimal getPreis() {
		return preis;
	}

	public void setPreis(BigDecimal preis) {
		this.preis = preis;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public Date getLastModificationDate() {
		return lastModificationDate;
	}

	public long getId() {
		return id;
	}
}
